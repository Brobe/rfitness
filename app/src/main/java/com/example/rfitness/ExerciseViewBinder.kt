package com.example.rfitness

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView


class ExerciseViewBinder(
    private val inflater: LayoutInflater
) {
    fun bind(parent: LinearLayout, exercise: Exercise): ExerciseViewState {

        val view = inflater.inflate(R.layout.view_exercise, parent, false)

        val title = view.findViewById<TextView>(R.id.exerciseTitle)
        val setsContainer = view.findViewById<LinearLayout>(R.id.setsContainer)

        title.text = exercise.name

        val baseWeight = WorkoutCalculator.weightForExercise(exercise)

        exercise.repsPerSet.forEachIndexed { index, reps ->
            val setView = inflater.inflate(R.layout.view_set_row, setsContainer, false)
            val setText = setView.findViewById<TextView>(R.id.setText)
            val weightInput = setView.findViewById<EditText>(R.id.setWeightInput)

            val initialWeight = baseWeight ?: 0.0

            setText.text = formatSet(index, reps)
            weightInput.setText("%.1f".format(initialWeight))

            if (exercise is Exercise.PowerExercise) {
                weightInput.apply { 
                    isFocusable = false
                    isFocusableInTouchMode = false
                    isClickable = false
                }
            } else {
                weightInput.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {}

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {}

                    override fun afterTextChanged(s: Editable?) {
                        val newWeight = s?.toString()?.toDoubleOrNull() ?: return

                        // Update current set text
                        setText.text = formatSet(index, reps)

                        // Propagate to following sets
                        for (i in index + 1 until setsContainer.childCount) {
                            val nextSet = setsContainer.getChildAt(i)
                            val nextText = nextSet.findViewById<TextView>(R.id.setText)
                            val nextInput = nextSet.findViewById<EditText>(R.id.setWeightInput)

                            nextText.text = formatSet(i, exercise.repsPerSet[i])

                            if (nextInput.text.toString() != "%.1f".format(newWeight)) {
                                nextInput.setText("%.1f".format(newWeight))
                            }
                        }
                    }
                })
            }

            setsContainer.addView(setView)
        }

        parent.addView(view)
        return ExerciseViewState(view)
    }

    private fun formatSet(index: Int, reps: Int): String {
        return "Set ${index + 1}: $reps reps ->"
    }
}
