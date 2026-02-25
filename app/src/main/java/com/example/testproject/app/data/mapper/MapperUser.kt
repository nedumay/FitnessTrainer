package com.example.testproject.app.data.mapper

import com.example.testproject.app.data.database.model.UserDbModel
import com.example.testproject.app.domain.model.user.User
import javax.inject.Inject

class MapperUser @Inject constructor() {

    fun mapEntityToDbModel(user: User, uid: String) = UserDbModel(
        id = uid,
        name = user.name,
        lastName = user.lastName,
        gender = user.gender,
        dateOfBirth = user.dateOfBirth,
        height = user.height,
        weight = user.weight,
        targetWeight = user.targetWeight,
        email = user.email,
    )

    fun mapDbModelToEntity(userDbModel: UserDbModel?) = User(
        id = userDbModel?.id ?: "",
        name = userDbModel?.name ?: "",
        lastName = userDbModel?.lastName ?: "",
        gender = userDbModel?.gender ?: false,
        dateOfBirth = userDbModel?.dateOfBirth ?: "",
        height = userDbModel?.height ?: "",
        weight = userDbModel?.weight ?: "",
        targetWeight = userDbModel?.targetWeight ?: "",
        email = userDbModel?.email ?: "",
    )
}