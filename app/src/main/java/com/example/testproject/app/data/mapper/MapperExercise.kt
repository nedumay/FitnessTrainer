package com.example.testproject.app.data.mapper

import com.example.testproject.app.data.model.ExerciseDbModel
import com.example.testproject.app.domain.model.beginner.Exercise

class MapperExercise {

    fun mapEntityToDbModel(exercise: Exercise) = ExerciseDbModel(
        id = exercise.id,
        name = exercise.name,
        subtitle = exercise.subtitle,
        video = exercise.video,
        desc = exercise.desc,
        area = exercise.area,
        areaImg = exercise.areaImg
    )

    fun mapDbModelToEntity(exerciseDbModel: ExerciseDbModel?) = Exercise(
        id = exerciseDbModel?.id ?: 0,
        name = exerciseDbModel?.name ?: "",
        subtitle = exerciseDbModel?.subtitle ?: "",
        video = exerciseDbModel?.video ?: "",
        desc = exerciseDbModel?.desc ?: "",
        area = exerciseDbModel?.area ?: "",
        areaImg = exerciseDbModel?.areaImg ?: ""

    )
}