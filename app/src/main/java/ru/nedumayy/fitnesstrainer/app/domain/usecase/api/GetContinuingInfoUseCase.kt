package ru.nedumayy.fitnesstrainer.app.domain.usecase.api

import ru.nedumayy.fitnesstrainer.app.domain.repository.RepositoryApi
import javax.inject.Inject

/**
 * @author Nedumayy (Samim)
 * Get Continuing info use case for work with data from remote.
 */
class GetContinuingInfoUseCase @Inject constructor(private val repositoryApi: RepositoryApi) {
    suspend operator fun invoke() = repositoryApi.getContinuingInfo()
}