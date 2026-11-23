package ru.nedumayy.fitnesstrainer.app.domain.usecase.firebase

import ru.nedumayy.fitnesstrainer.app.domain.repository.RepositoryFirebase
import javax.inject.Inject

/**
 * @author Nedumayy (Samim)
 * Auth user from Firebase use case.
 */
class AuthUserFirebase @Inject constructor(private val repositoryFirebase: RepositoryFirebase) {
    operator fun invoke() = repositoryFirebase.authUserFirebase()
}