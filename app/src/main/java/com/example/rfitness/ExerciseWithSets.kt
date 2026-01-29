package com.example.rfitness

import androidx.room.Embedded
import androidx.room.Relation


data class ExerciseWithSets(
    @Embedded val exercise: ExerciseResultEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "exerciseResultId",
        entity = SetResultEntity::class
    )
    val sets: List<SetResultEntity>
)
