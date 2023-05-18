package com.example.testproject.app.presentation.workout.lvl.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.testproject.app.domain.model.beginner.Workout

object LvlWorkoutDiffCallback : DiffUtil.ItemCallback<Workout>() {
    override fun areItemsTheSame(oldItem: Workout, newItem: Workout): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Workout, newItem: Workout): Boolean {
        return oldItem == newItem
    }
}