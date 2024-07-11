package com.example.testproject.app.domain.usecase.firebase

import com.example.testproject.app.domain.repository.RepositoryFirebase
import javax.inject.Inject

/**
 * @author Nedumayy (Samim)
 * Login user to Firebase use case.
 */
class LoginUserToFirebase @Inject constructor(private val repositoryFirebase: RepositoryFirebase) {

    suspend operator fun invoke(email: String, password: String) =
        repositoryFirebase.loginUserToFirebase(email, password)

}