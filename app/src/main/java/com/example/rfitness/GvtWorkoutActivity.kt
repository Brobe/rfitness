package com.example.rfitness

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class GvtWorkoutActivity : AppCompatActivity() {

    private val exerciseViews = mutableListOf<ExerciseViewState>()
    private lateinit var workout: Workout
    
    override fun onCreate(savedInstaceState: Bundle?) {
        super.onCreate(savedInstaceState)
        setContentView(R.layout.gvt_workout)

        val db = DatabaseProvider.getDatabase(this)
        val repository = WorkoutRepository(db.workoutDao())

        val workoutTitle = findViewById<TextView>(R.id.workoutTitle)
        val container = findViewById<LinearLayout>(R.id.workoutContainer)
        val doneButton = findViewById<Button>(R.id.doneButton)
        val binder = ExerciseViewBinder(layoutInflater)

        lifecycleScope.launch {
            workout = createTodayWorkout(repository)
            workoutTitle.text = workout.title

            workout.exercises.forEach { exercise ->
                val state = binder.bind(container, exercise)
                exerciseViews.add(state)
            }
        }

        doneButton?.setOnClickListener {
            lifecycleScope.launch {
                val workoutSession = WorkoutSessionEntity(
                    workoutName = workout.title,
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

    private suspend fun createTodayWorkout(repository: WorkoutRepository): Workout {
        val gvtworkout = GvtWorkoutViewModel()
        val original = gvtworkout.workout

        val updatedExercises = original.exercises.map { exercise ->
            when (exercise) {
                is Exercise.GvtExercise -> {
                    val previousWeight = repository.getPreviousWeightForExercise(exercise.name)
                    exercise.copy(previousWeight = previousWeight)
                }
                else -> exercise
            }
        }
        return original.copy(exercises = updatedExercises)
    }

}
