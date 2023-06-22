package com.example.testproject.app.domain.usecase.firebase

import com.example.testproject.app.domain.repository.RepositoryFirebase
import javax.inject.Inject

/**
 * @author Nedumayy (Samim)
 * Auth user from Firebase use case.
 */
class AuthUserFirebase @Inject constructor(private val repositoryFirebase: RepositoryFirebase) {
    operator fun invoke(uid: String) = repositoryFirebase.authUserFirebase(uid)
}