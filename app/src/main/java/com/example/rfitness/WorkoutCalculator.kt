package com.example.rfitness

object WorkoutCalculator {

    fun weightForExercise(exercise: Exercise): Double? =
        when (exercise) {
            is Exercise.PowerExercise ->
                getCorrectWeight(exercise.oneRepMax * exercise.percentage)

            is Exercise.GvtExercise ->
                exercise.weight

        }

    private fun getCorrectWeight(percentageWeight: Double): Double? {
        val modWeight = percentageWeight % 10

        if (modWeight == 0.0)
            return percentageWeight
        else if (0 < modWeight && modWeight <= 2.5) 
            return (percentageWeight - modWeight) + 2.5
        else if (2.5 < modWeight && modWeight <= 5.0)
            return (percentageWeight - modWeight) + 5
        else if (5 < modWeight && modWeight <= 7.5)
            return (percentageWeight - modWeight) + 7.5
        else 
            return (percentageWeight - modWeight) + 10.0

    }

}
