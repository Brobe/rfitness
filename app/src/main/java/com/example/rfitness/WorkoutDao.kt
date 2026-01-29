package com.example.rfitness

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.Transaction

@Dao
interface WorkoutDao {

    @Transaction
    @Query("SELECT * FROM workout_session ORDER BY date DESC")
    suspend fun getFullWorkouts(): List<WorkoutWithExercises>

    @Query("SELECT * FROM workout_session ORDER BY date DESC")
    suspend fun getAllWorkouts(): List<WorkoutSessionEntity>

    @Insert
    suspend fun insertWorkout(workout: WorkoutSessionEntity): Long

    @Insert
    suspend fun insertExercises(exercises: List<ExerciseResultEntity>): List<Long>

    @Insert
    suspend fun insertSets(sets: List<SetResultEntity>)

    @Transaction
    suspend fun insertFullWorkout(
        workout: WorkoutSessionEntity,
        exercises: List<ExerciseResultEntity>,
        sets: List<SetResultEntity>
    ) {
        val workoutId = insertWorkout(workout)

        val exercisesWithWorkoutId = exercises.map {
            it.copy(workoutSessionId = workoutId)
        }

        val exerciseIds = insertExercises(exercisesWithWorkoutId)

        val setsWithExerciseIds = sets.map { set ->
            val exerciseId = exerciseIds[set.exerciseResultId.toInt()]
            set.copy(exerciseResultId = exerciseId)
        }
        insertSets(setsWithExerciseIds)
    }
}
