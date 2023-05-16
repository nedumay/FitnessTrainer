package com.example.testproject.app.data.model

import com.example.testproject.app.domain.model.beginner.Exercise

data class WorkoutDbModel(
    var id: Int? = 0,
    var title: String? = "",
    var picture: String? = "",
    var exercise: List<Exercise> = listOf()
)
