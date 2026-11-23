package ru.nedumayy.fitnesstrainer.app.domain.usecase.api

import ru.nedumayy.fitnesstrainer.app.domain.repository.RepositoryApi
import javax.inject.Inject

class GetWorkoutAdvancedListUseCase @Inject constructor(private val repositoryApi: RepositoryApi) {
    suspend operator fun invoke() = repositoryApi.getWorkoutInfoAdvancedList()
}