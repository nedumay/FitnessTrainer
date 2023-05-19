package com.example.testproject.app.domain.usecase.api

import com.example.testproject.app.domain.repository.RepositoryApi
import javax.inject.Inject

/**
 * @author Nedumayy (Samim)
 */
class GetExerciseInfoListUseCase @Inject constructor(private val repositoryApi: RepositoryApi) {
    suspend operator fun invoke(idExercisesList: Int) = repositoryApi.getExerciseInfoList(idExercisesList)
}