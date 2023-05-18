package com.example.testproject.app.domain.usecase.api

import com.example.testproject.app.domain.model.beginner.Exercise
import com.example.testproject.app.domain.repository.RepositoryApi
import javax.inject.Inject

/**
 * @author Nedumayy (Samim)
 */
class GetExerciseInfoUseCase @Inject constructor(private val repositoryApi: RepositoryApi) {
    suspend operator fun invoke(idExercise: Int) = repositoryApi.getExerciseInfo(idExercise)
}