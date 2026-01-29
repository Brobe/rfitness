package com.example.rfitness

import androidx.room.Embedded
import androidx.room.Relation

data class WorkoutWithExercises(
    @Embedded val workout: WorkoutSessionEntity,

    @Relation(
        entity = ExerciseResultEntity::class,
        parentColumn = "id",
        entityColumn = "workoutSessionId"
    )
    val exercises: List<ExerciseWithSets>
)

