package com.example.testproject.app.presentation.workout.list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.testproject.app.domain.model.beginner.Exercise
import com.example.testproject.databinding.ListItemExerciseBinding

class ExerciseAdapter : ListAdapter<Exercise, ExerciseViewHolder>(ExerciseDiffCallback) {

    var onExerciseClickListener: ((Exercise) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ListItemExerciseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = getItem(position)
        with(holder.binding) {
            with(exercise) {
                textViewNameExercise.text = name
                textViewCountExercise.text = subtitle
                root.setOnClickListener {
                    onExerciseClickListener?.invoke(this)
                }
            }
        }
    }
}