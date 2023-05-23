package com.example.testproject.app.domain.usecase.api

import com.example.testproject.app.domain.repository.RepositoryApi
import javax.inject.Inject

class GetContinuingInfoUseCase @Inject constructor(private val repositoryApi: RepositoryApi) {
    suspend operator fun invoke() = repositoryApi.getContinuingInfo()
}