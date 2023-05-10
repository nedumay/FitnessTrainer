package com.example.testproject.app.domain.usecase

import com.example.testproject.app.domain.repository.RepositoryFirebase
import javax.inject.Inject

/**
 * @author Nedumayy (Samim)
 * Delete user from Firebase use case.
 */
class DeleteUserFromFirebase @Inject constructor(private val repositoryFirebase: RepositoryFirebase) {

    operator fun invoke(id: String) = repositoryFirebase.deleteUserFromFirebase(id)
}