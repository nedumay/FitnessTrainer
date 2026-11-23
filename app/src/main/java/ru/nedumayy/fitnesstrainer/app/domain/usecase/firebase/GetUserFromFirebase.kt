package ru.nedumayy.fitnesstrainer.app.domain.usecase.firebase

import ru.nedumayy.fitnesstrainer.app.domain.repository.RepositoryFirebase
import javax.inject.Inject

/**
 * @author Nedumayy (Samim)
 * Get user from Firebase use case.
 */
class GetUserFromFirebase @Inject constructor(private val repositoryFirebase: RepositoryFirebase) {

    operator suspend fun invoke(currentId: String) = repositoryFirebase.getUserFromFirebase(currentId)
}