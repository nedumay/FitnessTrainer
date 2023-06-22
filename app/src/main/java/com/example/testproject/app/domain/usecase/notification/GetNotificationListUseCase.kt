package com.example.testproject.app.domain.usecase.notification

import com.example.testproject.app.domain.repository.RepositoryNotification
import javax.inject.Inject

class GetNotificationListUseCase @Inject constructor(private val repository: RepositoryNotification){

    operator suspend fun invoke(idUser: String) = repository.getNotificationList(idUser)
}