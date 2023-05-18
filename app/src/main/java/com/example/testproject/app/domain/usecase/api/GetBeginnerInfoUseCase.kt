package com.example.testproject.app.domain.usecase.api

import com.example.testproject.app.domain.repository.RepositoryApi
import javax.inject.Inject

/**
 * @author Nedumayy (Samim)
 * Get Beginner info use case for work with data from remote.
 */
class GetBeginnerInfoUseCase @Inject constructor(private val repositoryApi: RepositoryApi) {
    suspend operator fun invoke() = repositoryApi.getBeginnerInfo()
}