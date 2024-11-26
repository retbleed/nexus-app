package com.dasc.nexus.features.students

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.dasc.nexus.data.utils.Resource
import com.dasc.nexus.ui.components.common.BottomNavigationBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun StudentView(navController: NavController) {

    val viewModel: StudentViewmodel = koinViewModel()
    val studentsState by viewModel.studentState.collectAsState()

    BottomNavigationBar(navController) {
        when (studentsState) {
            is Resource.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is Resource.Success -> {
                val filteredList by remember { mutableStateOf(studentsState.data) }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(filteredList!!) { student ->
                        Text(text = student.name)
                    }
                }
            }

            is Resource.Error -> {
                Text("Error: ${studentsState.message}")
            }

            is Resource.Warning -> {
                Text("Warning")
            }
        }
    }
}
