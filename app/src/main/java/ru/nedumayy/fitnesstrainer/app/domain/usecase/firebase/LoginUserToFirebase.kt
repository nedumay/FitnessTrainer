package ru.nedumayy.fitnesstrainer.app.domain.usecase.firebase

import ru.nedumayy.fitnesstrainer.app.domain.repository.RepositoryFirebase
import javax.inject.Inject

/**
 * @author Nedumayy (Samim)
 * Login user to Firebase use case.
 */
class LoginUserToFirebase @Inject constructor(private val repositoryFirebase: RepositoryFirebase) {

    suspend operator fun invoke(email: String, password: String) =
        repositoryFirebase.loginUserToFirebase(email, password)

}