package com.dasc.nexus.features.login


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
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dasc.nexus.data.utils.Resource
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginView(navController: NavController) {
    val viewModel: LoginViewmodel = koinViewModel()
    val loginState by viewModel.loginState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "NEXUS",
                fontSize = 24.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            EmailTextField(email = email, onEmailChanged = { email = it })
            PasswordTextField(password = password, onPasswordChanged = { password = it })

            Spacer(modifier = Modifier.height(16.dp))

            LoginButton {
                viewModel.login(email, password)
                keyboardController?.hide()
            }

            when (loginState) {
                is Resource.Loading -> {

                }
                is Resource.Error -> {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Revisa por favor tu correo o contreseña.",
                        color = Color.Red,
                        fontSize = 16.sp
                    )
                }
                is Resource.Success -> {
                    if (loginState.data == true) {
                        navController.navigate("home")
                    }
                }
                else -> {}
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
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
    ) {
        Text(
            text = "Login",
            fontSize = 16.sp,
            color = Color.White
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailTextField(email: String, onEmailChanged: (String) -> Unit) {
    TextField(
        value = email,
        onValueChange = onEmailChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp)),
        singleLine = true,
        label = { Text("Email") },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color(0xFFF3F3F3),
            focusedIndicatorColor = Color.Black,
            unfocusedIndicatorColor = Color.Gray
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(password: String, onPasswordChanged: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = password,
        onValueChange = onPasswordChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp)),
        singleLine = true,
        label = { Text("Password") },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
        }),
        visualTransformation = PasswordVisualTransformation(),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color(0xFFF3F3F3),
            focusedIndicatorColor = Color.Black,
            unfocusedIndicatorColor = Color.Gray
        )
    )
}