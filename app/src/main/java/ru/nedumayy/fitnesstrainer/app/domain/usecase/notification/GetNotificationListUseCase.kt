package ru.nedumayy.fitnesstrainer.app.domain.usecase.notification

import ru.nedumayy.fitnesstrainer.app.domain.repository.RepositoryNotification
import javax.inject.Inject

class GetNotificationListUseCase @Inject constructor(private val repository: RepositoryNotification){

    operator suspend fun invoke(idUser: String) = repository.getNotificationList(idUser)
}