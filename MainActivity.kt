package com.example.dailyroutine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.dailyroutine.data.Routine
import com.example.dailyroutine.data.RoutineDatabase
import com.example.dailyroutine.ui.RoutineScreen
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var db: RoutineDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = RoutineDatabase.getDatabase(this)
        val dao = db.routineDao()

        setContent {
            val routines = remember { mutableStateListOf<Routine>() }

            LaunchedEffect(Unit) {
                dao.getAll().collect {
                    routines.clear()
                    routines.addAll(it)
                }
            }

            RoutineScreen(
                routines = routines,
                onToggle = { updated ->
                    lifecycleScope.launch { dao.update(updated) }
                },
                onAdd = { name ->
                    lifecycleScope.launch {
                        dao.insert(Routine(name = name))
                    }
                }
            )
        }
    }
}
