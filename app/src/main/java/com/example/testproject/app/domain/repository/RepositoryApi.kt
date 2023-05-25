package com.example.testproject.app.domain.repository

import com.example.testproject.app.domain.model.beginner.ListLvl
import com.example.testproject.app.domain.model.beginner.Exercise
import com.example.testproject.app.domain.model.beginner.Workout

/**
 * @author Nedumayy (Samim)
 * Repository Api for work with data from remote.
 */
interface RepositoryApi {

    suspend fun getBeginnerInfo(): ListLvl

    suspend fun getContinuingInfo(): ListLvl

    suspend fun getAdvancedInfo(): ListLvl

    suspend fun getWorkoutInfoBeginnerList(): List<Workout>

    suspend fun getWorkoutInfoContinuingList(): List<Workout>

    suspend fun getWorkoutInfoAdvancedList(): List<Workout>

    suspend fun getExerciseInfoList(idExercisesList: Int): List<Exercise>

    suspend fun getExerciseInfoDetail(idExercise: Int, idExercisesList: Int): Exercise

}