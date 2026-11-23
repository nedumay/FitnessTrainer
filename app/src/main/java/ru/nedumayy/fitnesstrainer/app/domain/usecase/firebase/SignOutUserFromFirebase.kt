package ru.nedumayy.fitnesstrainer.app.domain.usecase.firebase

import ru.nedumayy.fitnesstrainer.app.domain.repository.RepositoryFirebase
import javax.inject.Inject

/**
 * @author Nedumayy (Samim)
 * Sign out user from Firebase use case.
 */
class SignOutUserFromFirebase @Inject constructor(private val repositoryFirebase: RepositoryFirebase) {

    operator fun invoke() = repositoryFirebase.signOutUserFromFirebase()
}