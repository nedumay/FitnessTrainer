package com.example.testproject.app.presentation.workout.lvl

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.app.common.Resource
import com.example.testproject.app.domain.model.beginner.Workout
import com.example.testproject.app.domain.usecase.api.GetWorkoutInfoListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LvlViewModel @Inject constructor(
    private val getWorkoutInfo: GetWorkoutInfoListUseCase
) : ViewModel() {

    private var _workoutInfo = MutableStateFlow<Resource<List<Workout>>>(Resource.Loading)
    val workoutInfo = _workoutInfo.asStateFlow()

    fun loadWorkoutList() {
        viewModelScope.launch {
            try {
                _workoutInfo.value = Resource.Loading
                val data = getWorkoutInfo.invoke()
                _workoutInfo.value = Resource.Success(data)
            } catch (e: Exception) {
                _workoutInfo.value = Resource.Error(e.message.toString())
            }
            Log.d("LoadDataApi", "LvlViewModel: ${_workoutInfo.value}")
        }
    }
}