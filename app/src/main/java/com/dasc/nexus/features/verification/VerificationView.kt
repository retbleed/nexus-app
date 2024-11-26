package com.dasc.nexus.features.verification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.dasc.nexus.data.utils.Resource
import org.koin.androidx.compose.koinViewModel

@Composable
fun VerificationView(navController: NavController){
    val verificationViewmodel: VerificationViewmodel = koinViewModel()
    val verificationState by verificationViewmodel.verificationState.collectAsState()
    verificationViewmodel.verify()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (verificationState) {
                is Resource.Loading -> {
                    CircularProgressIndicator()
                }
                is Resource.Success -> {
                    if (verificationState.data == true) {
                        navController.navigate("home")
                    } else {
                        navController.navigate("login")
                    }
                }
                is Resource.Error -> {
                    Text("Error: ${verificationState.message}")
                }
                is Resource.Warning -> {
                    Text("Warning")
                }
            }
        }
    }
}