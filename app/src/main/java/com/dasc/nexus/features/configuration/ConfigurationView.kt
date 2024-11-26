package com.dasc.nexus.features.configuration

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.dasc.nexus.data.utils.Resource
import com.dasc.nexus.features.login.LoginButton
import com.dasc.nexus.ui.components.common.BottomNavigationBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun ConfigurationView(navController: NavController){

    val viewModel: ConfigurationViewmodel = koinViewModel()
    val logoutState by viewModel.logoutState.collectAsState()

    BottomNavigationBar(navController){
        LoginButton {
            viewModel.logout()
        }
    }


    when(logoutState){
        is Resource.Loading -> {

        }
        is Resource.Success -> {
            if (logoutState.data == true) {
                navController.navigate("login")
            }
        }
        is Resource.Error -> {
            Text("Error: ${logoutState.message}")
        }
        is Resource.Warning -> {
            Text("Warning")
        }
    }
}