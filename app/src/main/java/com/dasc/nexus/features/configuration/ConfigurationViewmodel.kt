package com.dasc.nexus.features.configuration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dasc.nexus.data.interfaces.IAuth
import com.dasc.nexus.data.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ConfigurationViewmodel(
    private val auth: IAuth,
) : ViewModel(){

    private val _logoutState = MutableStateFlow<Resource<Boolean>>(Resource.Loading())
    val logoutState: StateFlow<Resource<Boolean>> = _logoutState.asStateFlow()

    fun logout() = viewModelScope.launch {
        auth.logout().collect { resource ->
            _logoutState.value = resource
        }
    }
}