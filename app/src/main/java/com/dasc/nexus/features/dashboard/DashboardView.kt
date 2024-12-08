package com.dasc.nexus.features.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dasc.nexus.data.models.ScheduleEntity
import com.dasc.nexus.data.utils.Resource
import com.dasc.nexus.ui.components.common.BottomNavigationBar
import org.koin.androidx.compose.koinViewModel
import java.time.format.DateTimeFormatter

@Composable
fun DashboardView(navController: NavController) {

    val viewModel: DashboardViewmodel = koinViewModel()
    val scheduleState by viewModel.scheduleState.collectAsState()

    viewModel.getSchedules(1)

    BottomNavigationBar(navController){

        when(scheduleState){
            is Resource.Error -> {

            }
            is Resource.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is Resource.Success -> {
                LazyRow {
                    items(scheduleState.data ?: emptyList()) { schedule ->
                        ScheduleCard(schedule = schedule)
                    }
                }
            }
            is Resource.Warning -> {

            }
        }
    }
}


@Composable
fun ScheduleCard(schedule: ScheduleEntity) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(200.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = schedule.subject.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            val startTime = schedule.schedule_block.start_time.format(DateTimeFormatter.ofPattern("HH:mm"))
            val endTime = schedule.schedule_block.end_time.format(DateTimeFormatter.ofPattern("HH:mm"))
            Text(text = "$startTime - $endTime")

            Text(text = "Room: ${schedule.room.name}")
        }
    }
}