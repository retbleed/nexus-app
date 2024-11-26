package com.dasc.nexus.features.rooms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dasc.nexus.data.interfaces.IRoom
import com.dasc.nexus.data.models.RoomEntity
import com.dasc.nexus.data.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RoomViewmodel(
    private val room: IRoom
) : ViewModel(){

    private val _roomState = MutableStateFlow<Resource<List<RoomEntity>>>(Resource.Loading())
    val roomState: StateFlow<Resource<List<RoomEntity>>> = _roomState.asStateFlow()

    fun getRooms() = viewModelScope.launch {
        room.getRooms().collect { resource ->
            _roomState.value = resource
        }
    }
    private val _roomByIdState = MutableStateFlow<Resource<RoomEntity>>(Resource.Loading())
    val roomByIdState: StateFlow<Resource<RoomEntity>> = _roomByIdState.asStateFlow()

    fun getRoomById(id: Long) = viewModelScope.launch {
        room.getRoomById(id).collect { resource ->
            _roomByIdState.value = resource
        }
    }
}