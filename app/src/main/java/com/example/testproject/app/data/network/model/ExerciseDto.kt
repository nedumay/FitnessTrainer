package com.example.testproject.app.data.network.model

import com.example.testproject.app.domain.model.beginner.Exercise

data class ExerciseDto(
    var id: Int,
    var name: String,
    var subtitle: String,
    var video: String,
    var desc: String,
    var area: String,
    var areaImg: String
)

//map ExerciseDto to Exercise
fun ExerciseDto.toExercise(): Exercise {
    return Exercise(
        id = id,
        name = name,
        subtitle = subtitle,
        video = video,
        desc = desc,
        area = area,
        areaImg = areaImg
    )
}
