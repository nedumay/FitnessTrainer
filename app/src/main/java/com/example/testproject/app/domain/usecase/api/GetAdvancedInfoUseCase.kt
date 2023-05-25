package com.example.testproject.app.domain.usecase.api

import com.example.testproject.app.domain.repository.RepositoryApi
import javax.inject.Inject

/**
 * @author Nedumayy (Samim)
 * Get Advanced info use case for work with data from remote.
 */
class GetAdvancedInfoUseCase @Inject constructor(private val repositoryApi: RepositoryApi) {
    suspend operator fun invoke() = repositoryApi.getAdvancedInfo()
}