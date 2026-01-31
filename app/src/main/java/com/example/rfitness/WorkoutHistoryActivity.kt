package com.example.rfitness

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class WorkoutHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_history)

        val recyclerView = findViewById<RecyclerView>(R.id.historyRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val db = DatabaseProvider.getDatabase(this)
        val repository = WorkoutRepository(db.workoutDao())

        lifecycleScope.launch {
            val workouts = repository.getFullWorkoutHistory()
            recyclerView.adapter = WorkoutHistoryAdapter(workouts)
        }
    }
}
