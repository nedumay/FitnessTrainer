package com.example.testproject.app.domain.usecase

import com.example.testproject.app.domain.repository.RepositoryFirebase
import javax.inject.Inject

/**
 * @author Nedumayy (Samim)
 * Auth user from Firebase use case.
 */
class AuthUserFirebase @Inject constructor(private val repositoryFirebase: RepositoryFirebase) {
    operator fun invoke() = repositoryFirebase.authUserFirebase()
}