package com.dasc.nexus.ui.components.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dasc.nexus.ui.components.utils.BottomNavItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(
    navController: NavController,
    content: @Composable () -> Unit
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Students,
        BottomNavItem.Rooms,
        BottomNavItem.Configuration
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(painterResource(id = item.icon), contentDescription = item.label) },
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }, topBar = {
            val title = items.find { currentRoute == it.route }?.label ?: "Nexus"
            TopAppBar(title = { Text(text = title)})
        }
    ) { innerPadding ->
        Column (modifier = Modifier.padding(innerPadding)) {
            content()
        }
    }
}