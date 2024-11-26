package com.dasc.nexus.data.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.collections.map

class TokenDataStore(private val dataStore: DataStore<Preferences>) {

    companion object {
        val TOKEN_KEY = stringPreferencesKey("token")
    }

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }

    fun getToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN_KEY]
        }
    }
}