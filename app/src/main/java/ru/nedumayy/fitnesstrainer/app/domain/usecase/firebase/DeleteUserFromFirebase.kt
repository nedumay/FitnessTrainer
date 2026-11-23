package ru.nedumayy.fitnesstrainer.app.domain.usecase.firebase

import ru.nedumayy.fitnesstrainer.app.domain.repository.RepositoryFirebase
import javax.inject.Inject

/**
 * @author Nedumayy (Samim)
 * Delete user from Firebase use case.
 */
class DeleteUserFromFirebase @Inject constructor(private val repositoryFirebase: RepositoryFirebase) {

    operator fun invoke(id: String) = repositoryFirebase.deleteUserFromFirebase(id)
}