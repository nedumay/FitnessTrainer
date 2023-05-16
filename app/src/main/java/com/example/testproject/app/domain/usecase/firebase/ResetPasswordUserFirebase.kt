package com.example.testproject.app.domain.usecase.firebase

import com.example.testproject.app.domain.repository.RepositoryFirebase
import javax.inject.Inject

/**
 * @author Nedumayy (Samim)
 * Reset password user from Firebase use case.
 */
class ResetPasswordUserFirebase @Inject constructor(private val repositoryFirebase: RepositoryFirebase) {

    operator suspend fun invoke(email: String) = repositoryFirebase.resetPasswordUserToFirebase(email)
}