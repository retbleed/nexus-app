package com.dasc.nexus.features.verification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dasc.nexus.data.interfaces.IAuth
import com.dasc.nexus.data.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VerificationViewmodel(
    private val auth: IAuth
): ViewModel(){
    private val _verificationState = MutableStateFlow<Resource<Boolean>>(Resource.Loading())
    val verificationState: StateFlow<Resource<Boolean>> = _verificationState.asStateFlow()

    fun verify() = viewModelScope.launch {
        auth.verifyToken().collect { resource ->
            _verificationState.value = resource
        }
    }
}