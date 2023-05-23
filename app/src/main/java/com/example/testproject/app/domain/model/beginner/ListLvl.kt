package com.example.testproject.app.domain.model.beginner

import com.google.gson.annotations.SerializedName

data class ListLvl(
    @SerializedName("list_lvl")
    var listLvl: List<Workout>

)