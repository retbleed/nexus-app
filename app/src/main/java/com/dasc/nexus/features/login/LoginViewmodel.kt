package com.dasc.nexus.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dasc.nexus.data.interfaces.IAuth
import com.dasc.nexus.data.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewmodel(
    private val authRepository: IAuth
) : ViewModel() {

    private val _loginState = MutableStateFlow<Resource<Boolean>>(Resource.Loading())
    val loginState: StateFlow<Resource<Boolean>> = _loginState.asStateFlow()

    fun login(email: String, password: String) = viewModelScope.launch {
        authRepository.login(email, password).collect { resource ->
            _loginState.value = resource
        }
    }
}