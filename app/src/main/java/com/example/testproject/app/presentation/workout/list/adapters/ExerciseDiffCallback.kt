package com.example.testproject.app.presentation.workout.list.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.testproject.app.domain.model.beginner.Exercise

object ExerciseDiffCallback : DiffUtil.ItemCallback<Exercise>() {
    override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
        return oldItem == newItem
    }
}