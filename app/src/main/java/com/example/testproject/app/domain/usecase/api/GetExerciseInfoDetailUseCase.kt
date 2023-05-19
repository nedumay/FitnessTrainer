package com.example.testproject.app.domain.usecase.api

import com.example.testproject.app.domain.repository.RepositoryApi
import javax.inject.Inject

class GetExerciseInfoDetailUseCase @Inject constructor(private val repositoryApi: RepositoryApi) {
    suspend operator fun invoke(idExercise: Int, idExercisesList: Int) = repositoryApi.getExerciseInfoDetail(idExercise, idExercisesList)
}