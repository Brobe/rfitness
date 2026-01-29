package com.example.rfitness

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class WorkoutHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val textView = TextView(this).apply {
            setPadding(24, 24, 24, 24)
            textSize = 16f
        }
        setContentView(textView)

        val db = DatabaseProvider.getDatabase(this)
        val repository = WorkoutRepository(db.workoutDao())

        lifecycleScope.launch {
            val workouts = repository.getFullWorkoutHistory()

            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

            textView.text = workouts.joinToString("\n\n") { workout ->
                buildString {
                    appendLine(workout.workout.workoutName)
                    appendLine(formatter.format(Date(workout.workout.date)))

                    workout.exercises.forEach { exercise ->
                        appendLine(exercise.exercise.exerciseName)

                        if (exercise.exercise.skipped) {
                            appendLine("  (Skipped)")
                        } else {
                            exercise.sets.forEach { set ->
                                appendLine(
                                    "  Set ${set.setIndex + 1}: " +
                                    "${set.reps} reps -> ${set.weight} kg"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
