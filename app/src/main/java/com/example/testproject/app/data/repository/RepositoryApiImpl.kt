package com.example.testproject.app.data.repository

import android.util.Log
import com.example.testproject.app.data.network.ApiService
import com.example.testproject.app.data.network.model.toBeginner
import com.example.testproject.app.domain.model.beginner.Beginner
import com.example.testproject.app.domain.model.beginner.Exercise
import com.example.testproject.app.domain.model.beginner.Workout
import com.example.testproject.app.domain.repository.RepositoryApi
import javax.inject.Inject

/**
 * @author Nedumayy (Samim)
 * RepositoryImpl for work with API.
 */
class RepositoryApiImpl @Inject constructor(
    private val apiService: ApiService
) : RepositoryApi {

    override suspend fun getBeginnerInfo(): Beginner {
        val beginnerList = apiService.getBeginnerInfo()
        Log.d("LoadDataApi", "Get beginner info: $beginnerList")
        return beginnerList.toBeginner()
    }

    override suspend fun getWorkoutInfo(): List<Workout> {
        val workoutList = getBeginnerInfo().beginner
        Log.d("LoadDataApi", "Get workout list: $workoutList")
        return workoutList
    }

    override suspend fun getExerciseInfo(idExercise: Int): List<Exercise> {
        val exercise = arrayListOf<Exercise>()
        getWorkoutInfo().map {
            if (it.id == idExercise) {
                it.exercise.map {
                    exercise.add(it)
                }
            }
        }
        return exercise
    }

}