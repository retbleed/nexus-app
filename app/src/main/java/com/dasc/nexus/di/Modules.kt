package com.dasc.nexus.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.dasc.nexus.data.interfaces.IAuth
import com.dasc.nexus.data.interfaces.IGroup
import com.dasc.nexus.data.interfaces.IRoom
import com.dasc.nexus.data.interfaces.ISchedule
import com.dasc.nexus.data.interfaces.IStudent
import com.dasc.nexus.data.interfaces.ITeacher
import com.dasc.nexus.data.repositories.AuthRepository
import com.dasc.nexus.data.repositories.GroupRepository
import com.dasc.nexus.data.repositories.RoomRepository
import com.dasc.nexus.data.repositories.ScheduleRepository
import com.dasc.nexus.data.repositories.StudentRepository
import com.dasc.nexus.data.repositories.TeacherRepository
import com.dasc.nexus.data.utils.TokenDataStore
import com.dasc.nexus.features.configuration.ConfigurationViewmodel
import com.dasc.nexus.features.dashboard.DashboardViewmodel
import com.dasc.nexus.features.login.LoginViewmodel
import com.dasc.nexus.features.rooms.RoomViewmodel
import com.dasc.nexus.features.students.StudentViewmodel
import com.dasc.nexus.features.verification.VerificationViewmodel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

private const val USER_PREFERENCES = "user_preferences"

val appModule = module {
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(androidContext(), USER_PREFERENCES)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { androidContext().preferencesDataStoreFile(USER_PREFERENCES) }
        )
    }

    single { TokenDataStore(get()) }

    single {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(HttpTimeout)
            defaultRequest {
                url("http://192.168.1.73:8000/api")
            }
        }
    }

    single<IAuth>       { AuthRepository(get(), get()) }
    single<ITeacher>    { TeacherRepository(get(), get()) }
    single<IStudent>    { StudentRepository(get(), get()) }
    single<IRoom>       { RoomRepository(get(), get()) }
    single<IGroup>      { GroupRepository(get(), get()) }
    single<ISchedule>   { ScheduleRepository(get(), get()) }

    viewModel {
        DashboardViewmodel(
            scheduleRepository  = get()
        )
    }

    viewModel {
        VerificationViewmodel(
            auth                = get()
        )
    }

    viewModel {
        StudentViewmodel(
            student             = get()
        )
    }

    viewModel {
        ConfigurationViewmodel(
            auth                = get()
        )
    }

    viewModel {
        RoomViewmodel(
            room                = get()
        )
    }

    viewModel {
        LoginViewmodel(
            authRepository      = get()
        )
    }
}