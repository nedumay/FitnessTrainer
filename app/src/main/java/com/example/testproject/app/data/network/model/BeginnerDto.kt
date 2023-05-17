package com.example.testproject.app.data.network.model

import com.example.testproject.app.domain.model.beginner.Beginner
import com.example.testproject.app.domain.model.beginner.Workout

data class BeginnerDto(
    var beginner: List<Workout> = listOf()
)

// map BeginnerDto to Beginner
fun BeginnerDto.toBeginner(): Beginner {
    return Beginner(beginner = beginner)
}