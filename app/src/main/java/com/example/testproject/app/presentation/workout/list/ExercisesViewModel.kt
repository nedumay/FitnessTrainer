package com.example.testproject.app.presentation.workout.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.app.common.Resource
import com.example.testproject.app.domain.model.beginner.Exercise
import com.example.testproject.app.domain.usecase.api.GetExerciseInfoListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExercisesViewModel @Inject constructor(
    private val getExerciseInfoListUseCase: GetExerciseInfoListUseCase

) : ViewModel() {

    private var _exerciseInfoList = MutableStateFlow<Resource<List<Exercise>>>(Resource.Loading)
    val exerciseInfoList = _exerciseInfoList.asStateFlow()

    fun loadExerciseList(idExercise: Int){
        viewModelScope.launch {
            try {
                _exerciseInfoList.value = Resource.Loading
                val data = getExerciseInfoListUseCase.invoke(idExercisesList = idExercise)
                Log.d("LoadDataApi", "Exercises ViewModel: $data")
                _exerciseInfoList.value = Resource.Success(data)
            } catch (e: Exception) {
                _exerciseInfoList.value = Resource.Error(e.message.toString())
            }
            Log.d("LoadDataApi", "Exercises ViewModel: ${_exerciseInfoList.value}")
        }
    }
}