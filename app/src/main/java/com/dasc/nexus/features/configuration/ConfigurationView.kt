package com.dasc.nexus.features.configuration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dasc.nexus.data.utils.Resource
import com.dasc.nexus.features.login.LoginButton
import com.dasc.nexus.ui.components.common.BottomNavigationBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun ConfigurationView(navController: NavController) {

    val viewModel: ConfigurationViewmodel = koinViewModel()
    val logoutState by viewModel.logoutState.collectAsState()

    BottomNavigationBar(navController) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { viewModel.logout() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Log Out", color = Color.White)
            }

            when (logoutState) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    if (logoutState.data == true) {
                        navController.navigate("login") {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        }
                    }
                }
                is Resource.Error -> {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Error: ${logoutState.message}", color = Color.Red)
                }
                is Resource.Warning -> {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Warning", color = Color.Red)
                }
            }
        }
    }
}