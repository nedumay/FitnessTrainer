package com.example.testproject.app.data.mapper

import com.example.testproject.app.data.model.WorkoutDbModel
import com.example.testproject.app.domain.model.beginner.Exercise
import com.example.testproject.app.domain.model.beginner.Workout

class MapperWorkout {

    fun mapEntityToDbModel(workout: Workout) = WorkoutDbModel(
        id = workout.id,
        title = workout.title,
        picture = workout.picture,
        exercise = workout.exercise
    )

    fun mapDbModelToEntity(workoutDbModel: WorkoutDbModel?) = Workout(
        id = workoutDbModel?.id ?: 0,
        title = workoutDbModel?.title ?: "",
        picture = workoutDbModel?.picture ?: "",
        exercise = workoutDbModel?.exercise ?: listOf()
    )
}