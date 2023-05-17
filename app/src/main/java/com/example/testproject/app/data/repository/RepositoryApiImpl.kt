package com.example.testproject.app.data.repository

import android.util.Log
import com.example.testproject.app.data.network.ApiService
import com.example.testproject.app.data.network.model.WorkoutDto
import com.example.testproject.app.data.network.model.toBeginner
import com.example.testproject.app.domain.model.beginner.Beginner
import com.example.testproject.app.domain.model.beginner.Exercise
import com.example.testproject.app.domain.model.beginner.Workout
import com.example.testproject.app.domain.repository.RepositoryApi
import java.lang.Exception
import javax.inject.Inject

/**
 * @author Nedumayy (Samim)
 * RepositoryImpl for work with API.
 */
class RepositoryApiImpl @Inject constructor(
    private val apiService: ApiService
) : RepositoryApi {

    override suspend fun getBeginnerInfo(): Beginner {
        TODO("Not yet implemented")
    }

    override suspend fun getWorkoutInfo(): List<Workout> {
        TODO("Not yet implemented")
    }

    override suspend fun getExerciseInfo(): List<Exercise> {
        TODO("Not yet implemented")
    }

    override suspend fun loadData() {
        val beginnerList = apiService.getBeginnerInfo()
        Log.d("LoadDataApi", "Beginner: $beginnerList")
        val itemsBeginner = beginnerList.beginner
        Log.d("LoadDataApi", "List Workout: $itemsBeginner")
        val itemsWorkout = itemsBeginner.map {
            Log.d("LoadDataApi", "List Exercise: $it")
            it.exercise.map {
                Log.d("LoadDataApi", "Exercise: $it")
            }
        }

    }

}