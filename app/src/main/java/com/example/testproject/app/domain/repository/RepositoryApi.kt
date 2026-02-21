package com.example.testproject.app.domain.repository

import com.example.testproject.app.domain.model.beginner.ListLvl
import com.example.testproject.app.domain.model.beginner.Exercise
import com.example.testproject.app.domain.model.beginner.Workout

/**
 * @author Nedumayy (Samim)
 * Repository Api for work with data from remote.
 */
interface RepositoryApi {

    /**
     * Метод получения списка уровня тренировок (начинающий уровень)
     * @return ListLvl - список уровня
     */
    suspend fun getBeginnerInfo(): ListLvl

    /**
     * Метод получения списка уровня тренировок (продолжающий уровень)
     * @return ListLvl - список уровня
     */
    suspend fun getContinuingInfo(): ListLvl

    /**
     * Метод получения списка уровня тренировок (профессиональный уровень)
     * @return ListLvl - список уровня
     */
    suspend fun getAdvancedInfo(): ListLvl

    /**
     * Метод получения списка внтури уровня (начинающий)
     * @return ListLvl - список упражнений (главный)
     */
    suspend fun getWorkoutInfoBeginnerList(): List<Workout>

    /**
     * Метод получения списка внтури уровня (продолжающий)
     * @return ListLvl - список упражнений (главный)
     */
    suspend fun getWorkoutInfoContinuingList(): List<Workout>

    /**
     * Метод получения списка внтури уровня (профессиональный)
     * @return ListLvl - список упражнений (главный)
     */
    suspend fun getWorkoutInfoAdvancedList(): List<Workout>

    /**
     * Метод получения списка упражнений
     * @param idExercisesList - id из списка упражнений (главный)
     * @return ListLvl - список упражнений
     */
    suspend fun getExerciseInfoList(idExercisesList: Int): List<Exercise>

    /**
     * Метод получения данных об упражнении
     * @param idExercise - id упражнения
     * @param idExercisesList - id из списка упражнений (главный)
     * @return Exercise - данные об упражнении
     */
    suspend fun getExerciseInfoDetail(idExercise: Int, idExercisesList: Int): Exercise

}