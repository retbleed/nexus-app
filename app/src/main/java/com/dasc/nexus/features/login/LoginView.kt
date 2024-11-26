package com.dasc.nexus.features.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import com.dasc.nexus.data.utils.Resource
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginView(navController: NavController) {

    val viewModel: LoginViewmodel = koinViewModel()
    val loginState by viewModel.loginState.collectAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Surface(color = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.height(32.dp))

            Text(text = "Email", fontSize = 24.sp, color = MaterialTheme.colors.primary)
            EmailTextField(email = email, onEmailChanged = { email = it })

            Text(text = "Password", fontSize = 24.sp, color = MaterialTheme.colors.primary)
            EmailTextField(email = password, onEmailChanged = { password = it })

            Spacer(modifier = Modifier.height(16.dp))

            LoginButton{
                viewModel.login(email, password)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    when (loginState) {
        is Resource.Loading -> {

        }
        is Resource.Success -> {
            if (loginState.data == true) {
                navController.navigate("home")
            }
        }
        is Resource.Error -> {
            Text("Error: ${loginState.message}")
        }
        is Resource.Warning -> {
            Text("Warning")
        }
    }

}

@Composable
fun PopUpMessage(
    showPopup: Boolean,
    content: @Composable () -> Unit,
    onDismiss: () -> Unit
){
    if (showPopup){
        Popup (
            alignment = Alignment.Center,
            properties = PopupProperties(clippingEnabled = false, focusable = true),
            onDismissRequest = onDismiss
        ) {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column (modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    content()
                }
            }
        }
    }
}


@Composable
fun LoginButton(onLogin: () -> Unit) {
    Button(
        onClick = { onLogin() },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary)
    ) {
        Text(text = "Login", fontSize = 18.sp, color = Color.White)
    }
}

@Composable
fun EmailTextField(email: String, onEmailChanged: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        value = email,
        onValueChange = onEmailChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clip(RoundedCornerShape(8.dp)),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}