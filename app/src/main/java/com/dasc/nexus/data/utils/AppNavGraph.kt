package com.dasc.nexus.data.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dasc.nexus.features.configuration.ConfigurationView
import com.dasc.nexus.features.dashboard.DashboardView
import com.dasc.nexus.features.login.LoginView
import com.dasc.nexus.features.rooms.RoomView
import com.dasc.nexus.features.students.StudentView
import com.dasc.nexus.features.verification.VerificationView
import com.dasc.nexus.ui.components.utils.BottomNavItem

@Composable
fun AppNavGraph(navHostController: NavHostController){
    NavHost(navController = navHostController, startDestination = "verification"){

        // VERIFICATION
        composable("verification"){
            VerificationView(navHostController)
        }

        // LOGIN
        composable("login"){
            LoginView(navHostController)
        }

        composable(BottomNavItem.Home.route){
            DashboardView(navHostController)
        }

        composable(BottomNavItem.Students.route){
            StudentView(navHostController)
        }

        composable(BottomNavItem.Rooms.route){
            RoomView(navHostController)
        }

        // MAESTROS
        composable(BottomNavItem.Teachers.route){
            DashboardView(navHostController)
        }

        // MATERIAS
        composable(BottomNavItem.Subjects.route){
            DashboardView(navHostController)
        }

        composable(BottomNavItem.Configuration.route){
            ConfigurationView(navHostController)
        }
    }
}

/**
 * SALONES -> EDIFICIOS, MAPAS
 * CARRERA
 * MAESTROS
 * MATERIAS
 * ALUMNOS
 */