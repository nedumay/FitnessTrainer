package com.example.testproject.app.domain.usecase

import com.example.testproject.app.common.Resource
import com.example.testproject.app.data.model.toUser
import com.example.testproject.app.domain.model.User
import com.example.testproject.app.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.net.HttpRetryException
import javax.inject.Inject

class GetUserFromFirebase @Inject constructor(private val repository: Repository) {

    operator fun invoke(email: String): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading<User>())
            val user = repository.getUserFromFirebase(email).toUser()
            emit(Resource.Success<User>(user))

        } catch (e: HttpRetryException){
            emit(Resource.Error<User>(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e : IOException){
            emit(Resource.Error<User>("Couldn't reach server. Check your internet connection."))
        }
    }
}