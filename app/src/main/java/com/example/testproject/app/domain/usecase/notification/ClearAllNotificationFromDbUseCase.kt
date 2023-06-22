package com.example.testproject.app.domain.usecase.notification

import com.example.testproject.app.domain.repository.RepositoryNotification
import javax.inject.Inject

class ClearAllNotificationFromDbUseCase @Inject constructor(private val repository: RepositoryNotification) {

    suspend operator fun invoke() = repository.clearDataBase()
}