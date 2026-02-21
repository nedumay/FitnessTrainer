package com.example.testproject.app.presentation.workout.lvl.adapters

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.testproject.app.domain.model.beginner.Workout
import com.example.testproject.databinding.ListItemWorkoutBinding

class LvlWorkoutViewHolder(val binding: ListItemWorkoutBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(workout: Workout, clickListener: ((Workout) -> Unit)?) {
        with(binding) {
            textViewWorkout.text = workout.title

            // ✅ Загрузка изображения через Coil
            imageViewWorkout.load(workout.picture) {
                //placeholder(R.drawable.ic_placeholder)  // Заглушка пока грузится
                //error(R.drawable.ic_error)              // Заглушка при ошибке
                crossfade(true)                         // Плавное появление
            }

            // ✅ Рейтинг
            ratingBarWorkout.rating = when {
                workout.id < 6 -> 1.0f
                workout.id in 6..10 -> 2.0f
                else -> 3.0f
            }

            // ✅ Клик
            root.setOnClickListener {
                clickListener?.invoke(workout)
            }
        }
    }

}