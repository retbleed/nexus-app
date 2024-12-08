package com.dasc.nexus.features.rooms

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dasc.nexus.R
import com.dasc.nexus.data.models.RoomEntity
import com.dasc.nexus.data.utils.Resource
import com.dasc.nexus.ui.components.common.BottomNavigationBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun RoomView(navController: NavController) {
    val viewModel: RoomViewmodel = koinViewModel()
    val roomsState by viewModel.roomState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    viewModel.getRooms()

    BottomNavigationBar(navController) {
        when (roomsState) {
            is Resource.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is Resource.Success -> {
                val rooms = roomsState.data ?: emptyList()
                Column(modifier = Modifier.fillMaxSize().padding(4.dp)) {
                    SearchField(query = searchQuery, onQueryChange = { searchQuery = it })

                    Spacer(modifier = Modifier.height(8.dp))

                    val filteredRooms = rooms.filter {
                        it.name.contains(searchQuery, ignoreCase = true)
                    }

                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(filteredRooms) { room ->
                            RoomItem(room = room, onClick = {
                                navController.navigate("room/${room.id}")
                            })
                        }
                    }
                }
            }

            is Resource.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${roomsState.message}")
                }
            }

            is Resource.Warning -> {

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomDetailView(navController: NavController, id: Long) {
    val viewModel: RoomViewmodel = koinViewModel()
    val roomByIdState by viewModel.roomByIdState.collectAsState()
    viewModel.getRoomById(id)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalles") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            when (roomByIdState) {
                is Resource.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is Resource.Success -> {
                    val room = roomByIdState.data
                    if (room != null) {
                        RoomDetails(room = room)
                    }
                }

                is Resource.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Error: ${roomByIdState.message}")
                    }
                }

                is Resource.Warning -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Warning")
                    }
                }
            }
        }
    }
}

@Composable
fun RoomItem(room: RoomEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = room.name, fontSize = 16.sp)
            Text(text = "Capacity: ${room.capacity}", fontSize = 14.sp)
            Text(text = "Floor: ${room.floor}", fontSize = 14.sp)
        }
    }
}

@Composable
fun RoomDetails(room: RoomEntity) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = room.name, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = room.long_desc, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Capacity: ${room.capacity}", fontSize = 16.sp)
        Text(text = "Floor: ${room.floor}", fontSize = 16.sp)
        Text(text = "Building: ${room.building}", fontSize = 16.sp)
        Text(text = "Type: ${room.type}", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {  },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
        ) {
            androidx.compose.material.Text("Solicitar", color = Color.White)
        }

        Image(
            painter = painterResource(id = R.drawable.photo),
            contentDescription = "Room Photo",
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun SearchField(query: String, onQueryChange: (String) -> Unit) {
    BasicTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray, MaterialTheme.shapes.small)
            .padding(8.dp),
        decorationBox = { innerTextField ->
            if (query.isEmpty()) {
                Text("Buscar...", color = Color.Gray, fontSize = 14.sp)
            }
            innerTextField()
        }
    )
}
