package com.example.testproject.app.data.repository

import android.util.Log
import com.example.testproject.app.data.network.ApiService
import com.example.testproject.app.data.network.model.BeginnerDto
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
        val beginnerList: BeginnerDto = apiService.getBeginnerInfo()
        Log.d("LoadDataApi", "Get beginner info: $beginnerList")
        return beginnerList.toBeginner()
    }

    override suspend fun getWorkoutInfoList(): List<Workout> {
        val workoutList: List<Workout> = getBeginnerInfo().beginner
        Log.d("LoadDataApi", "Get workout list: $workoutList")
        return workoutList
    }

    override suspend fun getExerciseInfoList(idExercisesList: Int): List<Exercise> {
        val exercisesList = arrayListOf<Exercise>()
        getWorkoutInfoList().map {
            if (it.id == idExercisesList) {
                it.exercise.map {
                    exercisesList.add(it)
                }
            }
        }
        return exercisesList
    }

    override suspend fun getExerciseInfoDetail(idExercise: Int, idExercisesList: Int): Exercise {
        var exercise: Exercise = Exercise(
            id = 0,
            name = "",
            subtitle = "",
            video = "",
            desc = "",
            area = "",
            areaImg = ""
        )
        getExerciseInfoList(idExercisesList).map {
            if (it.id == idExercise) {
                exercise = it
            }
        }
        return exercise
    }

}