package ru.nedumayy.fitnesstrainer.app.domain.usecase.api

import ru.nedumayy.fitnesstrainer.app.domain.repository.RepositoryApi
import javax.inject.Inject

class GetExerciseInfoDetailUseCase @Inject constructor(private val repositoryApi: RepositoryApi) {
    suspend operator fun invoke(idExercise: Int, idExercisesList: Int) = repositoryApi.getExerciseInfoDetail(idExercise, idExercisesList)
}