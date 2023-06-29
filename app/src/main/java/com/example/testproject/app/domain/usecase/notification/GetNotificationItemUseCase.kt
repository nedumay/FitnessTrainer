package com.example.testproject.app.domain.usecase.notification

import com.example.testproject.app.domain.repository.RepositoryNotification
import javax.inject.Inject

class GetNotificationItemUseCase @Inject constructor(private  val repositoryNotification: RepositoryNotification) {

    suspend operator fun invoke(id: Int, idUser: String) = repositoryNotification.getNotificationItem(id, idUser)

    suspend operator fun invoke(id: Int) = repositoryNotification.getNotificationItem(id)

    suspend operator fun invoke(name: String) = repositoryNotification.getNotificationItem(name)
}