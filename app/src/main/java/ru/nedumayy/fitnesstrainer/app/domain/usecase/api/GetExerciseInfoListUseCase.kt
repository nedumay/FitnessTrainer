package ru.nedumayy.fitnesstrainer.app.domain.usecase.api

import ru.nedumayy.fitnesstrainer.app.domain.repository.RepositoryApi
import javax.inject.Inject

/**
 * @author Nedumayy (Samim)
 */
class GetExerciseInfoListUseCase @Inject constructor(private val repositoryApi: RepositoryApi) {
    suspend operator fun invoke(idExercisesList: Int) = repositoryApi.getExerciseInfoList(idExercisesList)
}