package com.example.rfitness

import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.EditText
import android.widget.TextView

class ExerciseViewState(
    private val root: android.view.View
) {
    fun isCompleted(): Boolean {
        val skip = root.findViewById<android.widget.CheckBox>(R.id.skipExerciseCheckbox)
        if (skip.isChecked) return true

        val sets = root.findViewById<android.widget.LinearLayout>(R.id.setsContainer)

        for (i in 0 until sets.childCount) {
            val checkbox = sets
                .getChildAt(i)
                .findViewById<android.widget.CheckBox>(R.id.setCheckbox)

            if (!checkbox.isChecked) return false
        }

        return true
    }

    fun toExerciseResult(
        workoutSessionId: Long,
        exerciseName: String,
        exerciseIndex: Int
    ): Pair<ExerciseResultEntity, List<SetResultEntity>> {

        val skip = root.findViewById<CheckBox>(R.id.skipExerciseCheckbox).isChecked
        val setsContainer = root.findViewById<LinearLayout>(R.id.setsContainer)

        val exerciseResult = ExerciseResultEntity(
            workoutSessionId = workoutSessionId,
            exerciseName = exerciseName,
            skipped = skip
        )

        val setResults = mutableListOf<SetResultEntity>()

        for (i in 0 until setsContainer.childCount) {
            val setView = setsContainer.getChildAt(i)

            val checkbox = setView.findViewById<CheckBox>(R.id.setCheckbox)
            val weightInput = setView.findViewById<EditText>(R.id.setWeightInput)
            val text = setView.findViewById<TextView>(R.id.setText).text.toString()

            //Change when reps can be set with EditText or should it just be the same and use buttons?
            // can also be changed to a seperate TextView?
            val reps = setView.getTag(R.id.tag_reps) as Int
            val weight = weightInput.text.toString().toDoubleOrNull() ?: 0.0

            setResults.add(
                SetResultEntity(
                    exerciseResultId = exerciseIndex.toLong(), // TEMP index
                    setIndex = i,
                    reps = reps,
                    weight = weight,
                    completed = checkbox.isChecked
                )
            )
        }
        return exerciseResult to setResults
    }
}
