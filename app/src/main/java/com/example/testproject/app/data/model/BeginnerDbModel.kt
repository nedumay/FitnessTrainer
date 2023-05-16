package com.example.testproject.app.data.model

import com.example.testproject.app.domain.model.beginner.Workout

data class BeginnerDbModel(
    var beginner: List<Workout> = listOf()
)