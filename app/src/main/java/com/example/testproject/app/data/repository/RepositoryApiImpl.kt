package com.example.testproject.app.data.repository

import android.util.Log
import com.example.testproject.app.data.network.ApiService
import com.example.testproject.app.data.network.model.ListLvlDto
import com.example.testproject.app.data.network.model.toListLvl
import com.example.testproject.app.domain.model.beginner.Exercise
import com.example.testproject.app.domain.model.beginner.ListLvl
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

    override suspend fun getBeginnerInfo(): ListLvl {
        val beginnerList: ListLvlDto = apiService.getBeginnerInfo()
        Log.d("LoadDataApi", "Get beginner info: $beginnerList")
        return beginnerList.toListLvl()
    }

    override suspend fun getContinuingInfo(): ListLvl {
        val continuingList: ListLvlDto = apiService.getContinuingInfo()
        Log.d("LoadDataApi", "Get continuing info: $continuingList")
        return continuingList.toListLvl()
    }

    override suspend fun getWorkoutInfoBeginnerList(): List<Workout> {
        val workoutList: List<Workout> = getBeginnerInfo().listLvl
        Log.d("LoadDataApi", "Get workout list: $workoutList")
        return workoutList
    }

    override suspend fun getWorkoutInfoContinuingList(): List<Workout> {
        val workoutList: List<Workout> = getContinuingInfo().listLvl
        Log.d("LoadDataApi", "Get workout list: $workoutList")
        return workoutList
    }

    override suspend fun getExerciseInfoList(idExercisesList: Int): List<Exercise> {
        val exercisesList = arrayListOf<Exercise>()
        if(idExercisesList < 6){
            getWorkoutInfoBeginnerList().map {
                if (it.id == idExercisesList) {
                    it.exercise.map {
                        exercisesList.add(it)
                    }
                }
            }
        } else {
            getWorkoutInfoContinuingList().map {
                if (it.id == idExercisesList) {
                    it.exercise.map {
                        exercisesList.add(it)
                    }
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