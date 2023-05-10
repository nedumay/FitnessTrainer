package com.example.testproject.app.domain.usecase

import com.example.testproject.app.domain.model.User
import com.example.testproject.app.domain.repository.RepositoryFirebase
import javax.inject.Inject

/**
 * @author Nedumayy (Samim)
 * Add user to Firebase use case.
 */
class AddUserToFirebase @Inject constructor(private val repositoryFirebase: RepositoryFirebase) {
    suspend operator fun invoke(user: User) = repositoryFirebase.addUserToFirebase(user = user)

}