package com.example.testproject.app.data.network.model

import com.example.testproject.app.domain.model.beginner.Exercise
import com.example.testproject.app.domain.model.beginner.Workout

data class WorkoutDto(
    var id: Int,
    var title: String,
    var picture: String,
    var exercise: List<Exercise> = listOf()
)

//map WorkoutDto to Workout
fun WorkoutDto.toWorkout(): Workout {
    return Workout(
        id = id,
        title = title,
        picture = picture,
        exercise = exercise
    )
}
