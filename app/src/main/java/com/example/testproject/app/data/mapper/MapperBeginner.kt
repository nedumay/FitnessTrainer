package com.example.testproject.app.data.mapper

import com.example.testproject.app.data.model.BeginnerDbModel
import com.example.testproject.app.domain.model.beginner.Beginner

class MapperBeginner {

    fun mapEntityToDbModel(beginner: Beginner) = BeginnerDbModel(
        beginner = beginner.beginner
    )

    fun mapDbModelToEntity(beginner: BeginnerDbModel?) = Beginner(
        beginner = beginner?.beginner ?: listOf()
    )
}