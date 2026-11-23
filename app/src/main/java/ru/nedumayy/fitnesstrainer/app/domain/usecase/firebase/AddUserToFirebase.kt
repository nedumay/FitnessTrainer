package ru.nedumayy.fitnesstrainer.app.domain.usecase.firebase

import ru.nedumayy.fitnesstrainer.app.domain.model.user.User
import ru.nedumayy.fitnesstrainer.app.domain.repository.RepositoryFirebase
import javax.inject.Inject

/**
 * @author Nedumayy (Samim)
 * Add user to Firebase use case.
 */
class AddUserToFirebase @Inject constructor(private val repositoryFirebase: RepositoryFirebase) {
    suspend operator fun invoke(user: User, password: String) = repositoryFirebase.addUserToFirebase(user = user, password = password)

}