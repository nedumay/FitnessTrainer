package ru.nedumayy.fitnesstrainer.app.presentation.workout.list.adapters

import androidx.recyclerview.widget.DiffUtil
import ru.nedumayy.fitnesstrainer.app.domain.model.beginner.Exercise

object ExerciseDiffCallback : DiffUtil.ItemCallback<Exercise>() {
    override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
        return oldItem == newItem
    }
}