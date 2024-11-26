package com.dasc.nexus.ui.components.utils

import com.dasc.nexus.R

sealed class BottomNavItem (val route: String, val icon: Int, val label: String) {
    data object Home : BottomNavItem("home", R.drawable.home, "Inicio")
    data object Students : BottomNavItem("academic", R.drawable.academic, "Estudiantes")
    data object Rooms : BottomNavItem("rooms", R.drawable.building, "Salones")
    data object Configuration: BottomNavItem("config", R.drawable.cog, "Configuracion")
    data object Teachers: BottomNavItem("teachers", R.drawable.users, "Maestros")
    data object Subjects: BottomNavItem("subjects", R.drawable.document, "Materias")
}