package com.example.testproject.app.presentation.workout.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.app.common.Resource
import com.example.testproject.app.domain.model.beginner.Exercise
import com.example.testproject.app.domain.usecase.api.GetExerciseInfoDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val getExerciseInfoDetailUseCase: GetExerciseInfoDetailUseCase
) : ViewModel() {

    private var _exerciseInfo = MutableStateFlow<Resource<Exercise>>(Resource.Loading)
    val exerciseInfo = _exerciseInfo.asStateFlow()

    fun loadExerciseDetail(idExercise: Int, idExercisesList: Int) {
        viewModelScope.launch {
            try {
                _exerciseInfo.value = Resource.Loading
                val data = getExerciseInfoDetailUseCase.invoke(idExercise = idExercise, idExercisesList = idExercisesList)
                _exerciseInfo.value = Resource.Success(data)
            } catch (e: Exception) {
                _exerciseInfo.value = Resource.Error(e.message.toString())
            }
        }
        Log.d("LoadDataApi", "Detail ViewModel: ${_exerciseInfo.value}")
    }

}