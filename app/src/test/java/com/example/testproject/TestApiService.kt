package com.example.testproject

import com.example.testproject.app.data.network.ApiService
import com.example.testproject.app.data.network.model.ApiResponse
import com.example.testproject.app.data.network.model.ListLvlDto
import com.example.testproject.app.domain.model.beginner.Exercise
import com.example.testproject.app.domain.model.beginner.Workout
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Nedumayy (Samim)
 * UnitTest for ApiService class (https://api.myjson.online/v1/records/)
 */

class TestApiService {

    private lateinit var retrofit: Retrofit
    private lateinit var apiService: ApiService

    @Before
    fun setup() {
        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.myjson.online/v1/records/")
            .build()

        apiService = mock(ApiService::class.java)

    }

    @Test
    fun testGetBeginnerInfo() = runBlocking {
        val exercise = Exercise(
            id = 1,
            name = "name",
            subtitle = "subtitle",
            video = "video",
            desc = "desc",
            area = "area",
            areaImg = "areaImg"
        )
        val workout = Workout(
            id = 1,
            title = "title",
            picture = "picture",
            exercise = listOf(exercise)
        )
        val listLvlDto = ListLvlDto(listLvl = listOf(workout))
        val response = ApiResponse(listLvlDto, "id", "displayName", 1)
        Mockito.`when`(apiService.getBeginnerInfo()).thenReturn(response)

        val result = apiService.getBeginnerInfo()

        assertEquals(response, result)
    }

    @Test
    fun testGetContinuingInfo() = runBlocking {
        val exercise = Exercise(
            id = 1,
            name = "name",
            subtitle = "subtitle",
            video = "video",
            desc = "desc",
            area = "area",
            areaImg = "areaImg"
        )
        val workout = Workout(
            id = 1,
            title = "title",
            picture = "picture",
            exercise = listOf(exercise)
        )
        val listLvlDto = ListLvlDto(listLvl = listOf(workout))
        val response = ApiResponse(listLvlDto, "id", "displayName", 1)
        Mockito.`when`(apiService.getContinuingInfo()).thenReturn(response)

        // Вызов метода
        val result = apiService.getContinuingInfo()

        // Проверка результата
        assertEquals(response, result)
    }

    @Test
    fun testGetAdvancedInfo() = runBlocking {
        val exercise = Exercise(
            id = 1,
            name = "name",
            subtitle = "subtitle",
            video = "video",
            desc = "desc",
            area = "area",
            areaImg = "areaImg"
        )
        val workout = Workout(
            id = 1,
            title = "title",
            picture = "picture",
            exercise = listOf(exercise)
        )
        val listLvlDto = ListLvlDto(listLvl = listOf(workout))
        val response = ApiResponse(listLvlDto, "id", "displayName", 1)
        Mockito.`when`(apiService.getAdvancedInfo()).thenReturn(response)

        val result = apiService.getAdvancedInfo()

        assertEquals(response, result)
    }
}