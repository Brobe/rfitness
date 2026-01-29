package com.example.rfitness

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        WorkoutSessionEntity::class,
        ExerciseResultEntity::class,
        SetResultEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
}
