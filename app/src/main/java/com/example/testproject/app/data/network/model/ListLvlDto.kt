package com.example.testproject.app.data.network.model

import com.example.testproject.app.domain.model.beginner.ListLvl
import com.example.testproject.app.domain.model.beginner.Workout
import com.google.gson.annotations.SerializedName

data class ListLvlDto(
    @SerializedName("list_lvl")
    var listLvl: List<Workout> = listOf()
)

// map BeginnerDto to Beginner
fun ListLvlDto.toListLvl(): ListLvl {
    return ListLvl(listLvl = listLvl)
}