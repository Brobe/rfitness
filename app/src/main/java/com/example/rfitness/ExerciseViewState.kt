package com.example.rfitness

import android.widget.CheckBox
import android.widget.LinearLayout

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
}
