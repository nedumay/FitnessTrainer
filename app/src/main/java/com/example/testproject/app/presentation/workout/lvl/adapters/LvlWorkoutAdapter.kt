package com.example.testproject.app.presentation.workout.lvl.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.testproject.app.domain.model.beginner.Workout
import com.example.testproject.databinding.ListItemWorkoutBinding

/**
 * @author Nedumayy (Samim)
 * List adapter for work with data from remote.
 */
class LvlWorkoutAdapter : ListAdapter<Workout, LvlWorkoutViewHolder>(LvlWorkoutDiffCallback) {

    var onWorkoutClickListener: ((Workout) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LvlWorkoutViewHolder {
        val binding = ListItemWorkoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LvlWorkoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LvlWorkoutViewHolder, position: Int) {
        val workout = getItem(position)
        holder.bind(workout, onWorkoutClickListener)
    }

}