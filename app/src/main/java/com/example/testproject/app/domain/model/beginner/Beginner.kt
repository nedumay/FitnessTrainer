package com.example.testproject.app.domain.model.beginner

import com.google.gson.annotations.SerializedName

data class Beginner(
    @SerializedName("beginner")
    var beginner: List<Workout>

)