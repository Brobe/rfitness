package com.example.rfitness


sealed class Exercise {

    abstract val name: String
    abstract val repsPerSet: List<Int>
    abstract val weight: Double?

    data class PowerExercise(
        override val name: String,
        val oneRepMax: Double,
        val percentage: Double,
        override val repsPerSet: List<Int>
    ) : Exercise() {

        override val weight: Double
            get() = oneRepMax * percentage
    }

    data class GvtExercise(
        override val name: String,
        override val repsPerSet: List<Int>,
        override val weight: Double? = null,
        val previousWeight: List<Double>? = null,
        val previousRepsPerSet: List<Int>? = null
    ) : Exercise()

}
