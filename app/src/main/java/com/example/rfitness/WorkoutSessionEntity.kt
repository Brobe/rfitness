package com.example.rfitness

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(tableName = "workout_session")
data class WorkoutSessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val workoutName: String,

    val date: Long // System.currentTimeMillis()
)

@Entity(
    tableName = "exercise_results",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutSessionEntity::class,
            parentColumns = ["id"],
            childColumns = ["workoutSessionId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("workoutSessionId")]
)
data class ExerciseResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val workoutSessionId: Long,

    val exerciseName: String,

    val skipped: Boolean
)

@Entity(
    tableName = "set_results",
    foreignKeys = [
        ForeignKey(
            entity = ExerciseResultEntity::class,
            parentColumns = ["id"],
            childColumns = ["exerciseResultId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("exerciseResultId")]
)
data class SetResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val exerciseResultId: Long,

    val setIndex: Int,

    val reps: Int,

    val weight: Double,

    val completed: Boolean
)
