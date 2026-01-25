package com.example.rfitness

import androidx.lifecycle.ViewModel


class GvtWorkoutViewModel : ViewModel() {

    val workout: Workout = Workout(
        title = "Deadlift + Arms Day",
        exercises = listOf(
            Exercise.PowerExercise(
                name = "Deadlift",
                oneRepMax = 180.0,
                percentage = 0.8,
                repsPerSet = listOf(5,5,5)
            ),
            Exercise.GvtExercise(
                name = "Tricep Extensions",
                repsPerSet = listOf(12, 11, 10, 10, 9, 8)
            ),
            Exercise.GvtExercise(
                name = "Bicep Curl",
                repsPerSet = listOf(12, 11, 10, 10, 9, 8)
            ),
            Exercise.GvtExercise(
                name = "Bent-over Tricep Extensions",
                repsPerSet = listOf(10, 10, 10)
            )
        )
    )
}
