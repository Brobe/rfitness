package com.example.rfitness

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import kotlin.collections.listOf

class GvtWorkoutActivity : AppCompatActivity() {

    private val exerciseViews = mutableListOf<ExerciseViewState>()
    
    override fun onCreate(savedInstaceState: Bundle?) {
        super.onCreate(savedInstaceState)
        setContentView(R.layout.gvt_workout)


        val workoutTitle = findViewById<TextView>(R.id.workoutTitle)
        val container = findViewById<LinearLayout>(R.id.workoutContainer)
        val doneButton = findViewById<Button>(R.id.doneButton)

        val workout = createTodayWorkout()

        workoutTitle.text = workout.title

        val binder = ExerciseViewBinder(layoutInflater)

        workout.exercises.forEach { exercise ->
            val state = binder.bind(container, exercise)
            exerciseViews.add(state)
        }


        doneButton?.setOnClickListener {
            if (exerciseViews.all { it.isCompleted() }) {
                // Success (later: save workout)
                Toast.makeText(this, "Workout completed!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Finish all sets or skip exercises", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createTodayWorkout(): Workout {
        val gvtworkout = GvtWorkoutViewModel()
        return gvtworkout.workout
    }

}
