package com.dasc.nexus.features.students

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dasc.nexus.data.interfaces.IStudent
import com.dasc.nexus.data.models.StudentEntity
import com.dasc.nexus.data.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StudentViewmodel(
    private val student: IStudent,
): ViewModel() {
    private val _studentState = MutableStateFlow<Resource<List<StudentEntity>>>(Resource.Loading())
    val studentState: StateFlow<Resource<List<StudentEntity>>> = _studentState.asStateFlow()

    fun getStudents() = viewModelScope.launch {
        student.getStudents().collect { resource ->
            _studentState.value = resource
        }
    }

    private val _studentByIdState = MutableStateFlow<Resource<StudentEntity>>(Resource.Loading())
    val studentByIdState: StateFlow<Resource<StudentEntity>> = _studentByIdState.asStateFlow()

    fun getStudentById(id: Long) = viewModelScope.launch {
        student.getStudentById(id).collect { resource ->
            _studentByIdState.value = resource
        }
    }
}