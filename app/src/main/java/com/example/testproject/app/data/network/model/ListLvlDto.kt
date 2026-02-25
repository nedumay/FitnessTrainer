package com.example.testproject.app.data.network.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.example.testproject.app.domain.model.beginner.Exercise
import com.example.testproject.app.domain.model.beginner.ListLvl
import com.example.testproject.app.domain.model.beginner.Workout
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ListLvlDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("picture")
    val picture: String,

    @SerializedName("exercise")
    val exercise: List<ExerciseDto>
) : Parcelable

fun List<ListLvlDto>.toListLvl(): ListLvl {
    return ListLvl(
        listLvl = this.map { dto ->
            Workout(
                id = dto.id,
                title = dto.title,
                picture = dto.picture,
                exercise = dto.exercise.map { ex ->
                    Exercise(
                        id = ex.id,
                        name = ex.name,
                        subtitle = ex.subtitle,
                        video = ex.video,
                        desc = ex.desc,
                        area = ex.area,
                        areaImg = ex.areaImg
                    )
                }
            )
        }
    )
}