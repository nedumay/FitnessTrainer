package com.example.testproject.app.data.repository

import android.content.Context
import android.util.Log
import androidx.work.Data
import com.example.testproject.app.data.network.ApiService
import com.example.testproject.app.data.network.model.ApiResponse
import com.example.testproject.app.data.network.model.ListLvlDto
import com.example.testproject.app.data.network.model.toListLvl
import com.example.testproject.app.domain.model.beginner.Exercise
import com.example.testproject.app.domain.model.beginner.ListLvl
import com.example.testproject.app.domain.model.beginner.Workout
import com.example.testproject.app.domain.repository.RepositoryApi
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

/**
 * @author Nedumayy (Samim)
 * RepositoryImpl for work with API.
 */
private const val TAG = "RepositoryApiImpl"
class RepositoryApiImpl @Inject constructor(
    private val context: Context
) : RepositoryApi {

    private val gson = Gson()

    private suspend fun loadJsonFromAssets(fileName: String) : String {
        return withContext(Dispatchers.IO){
            context.assets.open(fileName)
                .bufferedReader()
                .use {it.readText()}
        }
    }

    private suspend fun parseApiResponse(json: String): ApiResponse {
        return withContext(Dispatchers.IO) {
            gson.fromJson(json, ApiResponse::class.java)
        }
    }
    override suspend fun getBeginnerInfo(): ListLvl {
        val json = loadJsonFromAssets("Beginner.json")
        val response = parseApiResponse(json)
        return response.listLvl.toListLvl()
    }

    override suspend fun getContinuingInfo(): ListLvl {
        val json = loadJsonFromAssets("Continuing.json")
        val response = parseApiResponse(json)
        return response.listLvl.toListLvl()
    }

    override suspend fun getAdvancedInfo(): ListLvl {
        val json = loadJsonFromAssets("Advanced.json")
        val response = parseApiResponse(json)
        return response.listLvl.toListLvl()
    }

    override suspend fun getWorkoutInfoBeginnerList(): List<Workout> {
        val workoutList: List<Workout> = getBeginnerInfo().listLvl
        return workoutList
    }

    override suspend fun getWorkoutInfoContinuingList(): List<Workout> {
        val workoutList: List<Workout> = getContinuingInfo().listLvl
        return workoutList
    }

    override suspend fun getWorkoutInfoAdvancedList(): List<Workout> {
        val workoutList: List<Workout> = getAdvancedInfo().listLvl
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
        } else if(idExercisesList in 6..10){
            getWorkoutInfoContinuingList().map {
                if (it.id == idExercisesList) {
                    it.exercise.map {
                        exercisesList.add(it)
                    }
                }
            }
        } else{
            getWorkoutInfoAdvancedList().map {
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
