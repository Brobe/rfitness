package com.example.rfitness

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import kotlin.collections.listOf
import kotlinx.coroutines.launch

class GvtWorkoutActivity : AppCompatActivity() {

    private val exerciseViews = mutableListOf<ExerciseViewState>()
    
    override fun onCreate(savedInstaceState: Bundle?) {
        super.onCreate(savedInstaceState)
        setContentView(R.layout.gvt_workout)

        val db = DatabaseProvider.getDatabase(this)
        val repository = WorkoutRepository(db.workoutDao())

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
            lifecycleScope.launch {
            

                val workoutSession = WorkoutSessionEntity(
                    workoutName = workoutTitle.text.toString(),
                    date = System.currentTimeMillis()
                )

                val exerciseResults = mutableListOf<ExerciseResultEntity>()
                val setResults = mutableListOf<SetResultEntity>()

                exerciseViews.forEachIndexed { index, state ->
                    val exercise = workout.exercises[index]

                    val (exerciseResult, sets) = 
                        state.toExerciseResult(
                            workoutSessionId = 0,
                            exerciseName = exercise.name,
                            exerciseIndex = index
                        )

                    exerciseResults.add(exerciseResult)
                    setResults.addAll(sets)
                }

                repository.saveWorkout(
                    workoutSession,
                    exerciseResults,
                    setResults
                )

                Toast.makeText(
                    this@GvtWorkoutActivity,
                    "Workout saved!",
                    Toast.LENGTH_SHORT
                ).show()

                finish()
            }
        }
    }

    private fun createTodayWorkout(): Workout {
        val gvtworkout = GvtWorkoutViewModel()
        return gvtworkout.workout
    }

}
