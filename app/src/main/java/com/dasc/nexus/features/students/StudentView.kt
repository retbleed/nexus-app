package com.dasc.nexus.features.students

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.dasc.nexus.data.models.StudentEntity
import com.dasc.nexus.data.utils.Resource
import com.dasc.nexus.ui.components.common.BottomNavigationBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun StudentView(navController: NavController) {

    val viewModel: StudentViewmodel = koinViewModel()
    val studentsState by viewModel.studentState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    viewModel.getStudents()

    BottomNavigationBar(navController) {
        when (studentsState) {
            is Resource.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is Resource.Success -> {
                val students = studentsState.data ?: emptyList()

                Column(modifier = Modifier.fillMaxSize().padding(4.dp)) {
                    SearchField(query = searchQuery, onQueryChange = { searchQuery = it })

                    Spacer(modifier = Modifier.height(8.dp))

                    val filteredList = students.filter {
                        it.name.contains(searchQuery, ignoreCase = true)
                    }

                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(filteredList) { student ->
                            StudentItem(student = student)
                        }
                    }
                }
            }

            is Resource.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${studentsState.message}")
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

@Composable
fun SearchField(query: String, onQueryChange: (String) -> Unit) {
    BasicTextField(
        value = query,
        onValueChange = onQueryChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
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

@Composable
fun StudentItem(student: StudentEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.Blue, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = student.getInitials(),
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = student.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Row (Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "IDS", fontSize = 14.sp)
                    Text(text = "Semestre 1", fontSize = 14.sp)
                    Text(text = "Matutino", fontSize = 14.sp)
                }
            }
        }
    }
}

fun StudentEntity.getInitials(): String {
    val names = name.split(" ")
    return when (names.size) {
        0 -> ""
        1 -> names[0].take(1).uppercase()
        else -> names[0].take(1).uppercase() + names[1].take(1).uppercase()
    }
}
