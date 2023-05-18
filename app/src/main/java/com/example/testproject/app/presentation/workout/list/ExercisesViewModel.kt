package com.example.testproject.app.presentation.workout.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.app.common.Resource
import com.example.testproject.app.domain.model.beginner.Exercise
import com.example.testproject.app.domain.model.beginner.Workout
import com.example.testproject.app.domain.usecase.api.GetExerciseInfoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExercisesViewModel @Inject constructor(
    private val getExerciseInfoUseCase: GetExerciseInfoUseCase

) : ViewModel() {

    private var _exerciseInfo = MutableStateFlow<Resource<List<Exercise>>>(Resource.Loading)
    val exerciseInfo = _exerciseInfo.asStateFlow()

    fun loadExerciseList(idExercise: Int){
        viewModelScope.launch {
            try {
                _exerciseInfo.value = Resource.Loading
                val data = getExerciseInfoUseCase.invoke(idExercise = idExercise)
                _exerciseInfo.value = Resource.Success(data)
            } catch (e: Exception) {
                _exerciseInfo.value = Resource.Error(e.message.toString())
            }
            Log.d("LoadDataApi", "Exercises ViewModel: ${_exerciseInfo.value}")
        }
    }
}