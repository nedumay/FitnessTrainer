package ru.nedumayy.fitnesstrainer.app.domain.usecase.notification

import ru.nedumayy.fitnesstrainer.app.domain.repository.RepositoryNotification
import javax.inject.Inject

class ClearAllNotificationFromDbUseCase @Inject constructor(private val repository: RepositoryNotification) {

    suspend operator fun invoke() = repository.clearDataBase()
}