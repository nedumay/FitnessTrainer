package com.example.testproject.app.domain.usecase.firebase

import com.example.testproject.app.domain.repository.RepositoryFirebase
import javax.inject.Inject

/**
 * @author Nedumayy (Samim)
 * Sign out user from Firebase use case.
 */
class SignOutUserFromFirebase @Inject constructor(private val repositoryFirebase: RepositoryFirebase) {

    operator fun invoke() = repositoryFirebase.signOutUserFromFirebase()
}