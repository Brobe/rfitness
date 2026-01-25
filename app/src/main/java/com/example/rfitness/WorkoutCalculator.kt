package com.example.rfitness

object WorkoutCalculator {

    fun weightForExercise(exercise: Exercise): Double? =
        when (exercise) {
            is Exercise.PowerExercise ->
                exercise.oneRepMax * exercise.percentage

            is Exercise.GvtExercise ->
                exercise.weight

        }

}
