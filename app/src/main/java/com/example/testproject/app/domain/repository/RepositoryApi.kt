package com.example.testproject.app.domain.repository

import com.example.testproject.app.domain.model.beginner.Beginner
import com.example.testproject.app.domain.model.beginner.Exercise
import com.example.testproject.app.domain.model.beginner.Workout

/**
 * @author Nedumayy (Samim)
 * Repository Api for work with data from remote.
 */
interface RepositoryApi {

    suspend fun getBeginnerInfo(): Beginner

    suspend fun getWorkoutInfoList(): List<Workout>

    suspend fun getExerciseInfoList(idExercisesList: Int): List<Exercise>

    suspend fun getExerciseInfoDetail(idExercise: Int, idExercisesList: Int): Exercise

}