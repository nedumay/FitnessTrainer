package ru.nedumayy.fitnesstrainer.app.domain.usecase.firebase

import ru.nedumayy.fitnesstrainer.app.domain.repository.RepositoryFirebase
import javax.inject.Inject

/**
 * @author Nedumayy (Samim)
 * Reset password user from Firebase use case.
 */
class ResetPasswordUserFirebase @Inject constructor(private val repositoryFirebase: RepositoryFirebase) {

    operator suspend fun invoke(email: String) = repositoryFirebase.resetPasswordUserToFirebase(email)
}