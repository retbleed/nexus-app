package com.dasc.nexus.features.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dasc.nexus.data.interfaces.ISchedule
import com.dasc.nexus.data.models.ScheduleEntity
import com.dasc.nexus.data.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DashboardViewmodel (
    private val scheduleRepository: ISchedule

): ViewModel(){
    private val _scheduleState = MutableStateFlow<Resource<List<ScheduleEntity>>>(Resource.Loading())
    val scheduleState: StateFlow<Resource<List<ScheduleEntity>>> = _scheduleState.asStateFlow()

    fun getSchedules(id: Long) = viewModelScope.launch {
        scheduleRepository.getScheduleById(id).collect { resource ->
            _scheduleState.value = resource
        }
    }
}