package com.example.dailyroutine.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dailyroutine.data.Routine
import kotlinx.coroutines.launch

@Composable
fun RoutineScreen(routines: List<Routine>, onToggle: (Routine) -> Unit, onAdd: (String) -> Unit) {
    var newTask by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp)) {
        Text("Daily Routine Tracker", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(16.dp))

        Row {
            TextField(
                value = newTask,
                onValueChange = { newTask = it },
                label = { Text("Add new task") },
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = {
                    if (newTask.isNotBlank()) {
                        onAdd(newTask)
                        newTask = ""
                    }
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Add")
            }
        }

        Spacer(Modifier.height(16.dp))

        routines.forEach { routine ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(routine.name)
                Checkbox(
                    checked = routine.isDone,
                    onCheckedChange = {
                        onToggle(routine.copy(isDone = it))
                    }
                )
            }
        }
    }
}
