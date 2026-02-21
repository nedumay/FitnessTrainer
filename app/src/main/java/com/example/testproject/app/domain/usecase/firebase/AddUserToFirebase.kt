package com.example.testproject.app.domain.usecase.firebase

import com.example.testproject.app.domain.model.user.User
import com.example.testproject.app.domain.repository.RepositoryFirebase
import javax.inject.Inject

/**
 * @author Nedumayy (Samim)
 * Add user to Firebase use case.
 */
class AddUserToFirebase @Inject constructor(private val repositoryFirebase: RepositoryFirebase) {
    suspend operator fun invoke(user: User, password: String) = repositoryFirebase.addUserToFirebase(user = user, password = password)

}