package com.example.rfitness

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class WorkoutHistoryAdapter(
    private val workouts: List<WorkoutWithExercises>
) : RecyclerView.Adapter<WorkoutHistoryAdapter.ViewHolder>() {
    
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.workoutTitle)
        val date: TextView = view.findViewById(R.id.workoutDate)
        val details: TextView = view.findViewById(R.id.workoutDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_workout_history, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = workouts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val workout = workouts[position]

        val formatter = SimpleDateFormat(
            "yyyy-MM-dd HH:mm",
            Locale.getDefault()
        )

        holder.title.text = workout.workout.workoutName
        holder.date.text = formatter.format(Date(workout.workout.date))

        holder.details.text = buildString {
            workout.exercises.forEach { exercise ->
                appendLine(exercise.exercise.exerciseName)

                if (exercise.exercise.skipped) {
                    appendLine("   (Skipped)")
                } else {
                    exercise.sets.forEach { set ->
                        appendLine(
                            "   Set ${set.setIndex + 1}: " +
                            "${set.reps} reps -> ${set.weight} kg"
                        )
                    }
                }
                appendLine()
            }
        }
    }
}
