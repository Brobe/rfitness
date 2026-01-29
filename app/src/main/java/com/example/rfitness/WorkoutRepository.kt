package com.example.rfitness

class WorkoutRepository(
    private val workoutDao: WorkoutDao
) {

    suspend fun saveWorkout(
        workout: WorkoutSessionEntity,
        exercises: List<ExerciseResultEntity>,
        sets: List<SetResultEntity>
    ) {
        workoutDao.insertFullWorkout(
            workout = workout,
            exercises = exercises,
            sets = sets
        )
    }

    suspend fun getFullWorkoutHistory(): List<WorkoutWithExercises> {
        return workoutDao.getFullWorkouts()
    }

    suspend fun getAllWorkouts(): List<WorkoutSessionEntity> {
        return workoutDao.getAllWorkouts()
    }
}
