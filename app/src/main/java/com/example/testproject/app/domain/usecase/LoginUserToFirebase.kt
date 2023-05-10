package com.example.testproject.app.domain.usecase

import com.example.testproject.app.domain.repository.RepositoryFirebase
import javax.inject.Inject

/**
 * @author Nedumayy (Samim)
 * Login user to Firebase use case.
 */
class LoginUserToFirebase @Inject constructor(private val repositoryFirebase: RepositoryFirebase) {

    operator suspend fun invoke(email: String, password: String) =
        repositoryFirebase.loginUserToFirebase(email, password)
}