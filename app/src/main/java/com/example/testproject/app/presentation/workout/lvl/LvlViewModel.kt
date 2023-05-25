package com.example.testproject.app.presentation.workout.lvl

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.app.common.Resource
import com.example.testproject.app.domain.model.beginner.Workout
import com.example.testproject.app.domain.usecase.api.GetAdvancedInfoUseCase
import com.example.testproject.app.domain.usecase.api.GetWorkoutAdvancedListUseCase
import com.example.testproject.app.domain.usecase.api.GetWorkoutInfoBeginnerListUseCase
import com.example.testproject.app.domain.usecase.api.GetWorkoutInfoContinuingListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LvlViewModel @Inject constructor(
    private val getWorkoutBeginnerInfo: GetWorkoutInfoBeginnerListUseCase,
    private val getWorkoutContinuingInfo: GetWorkoutInfoContinuingListUseCase,
    private val getWorkoutAdvancedInfo: GetWorkoutAdvancedListUseCase
) : ViewModel() {

    private var _beginnerLvlList = MutableStateFlow<Resource<List<Workout>>>(Resource.Loading)
    val beginnerLvlList = _beginnerLvlList.asStateFlow()

    private var _continuingLvlList = MutableStateFlow<Resource<List<Workout>>>(Resource.Loading)
    val continuingLvlList = _continuingLvlList.asStateFlow()

    private var _advancedLvlList = MutableStateFlow<Resource<List<Workout>>>(Resource.Loading)
    val advancedLvlList = _advancedLvlList.asStateFlow()

    fun loadWorkoutListBeginner() {
        viewModelScope.launch {
            try {
                _beginnerLvlList.value = Resource.Loading
                val dataBeginner = getWorkoutBeginnerInfo.invoke()
                _beginnerLvlList.value = Resource.Success(dataBeginner)

            } catch (e: Exception) {
                _beginnerLvlList.value = Resource.Error(e.message.toString())
            }
            Log.d("LoadDataApi", "LvlViewModel: ${beginnerLvlList.value}")
        }
    }

    fun loadWorkoutListContinuing() {
        viewModelScope.launch {
            try {
                _continuingLvlList.value = Resource.Loading
                val dataContinuing = getWorkoutContinuingInfo.invoke()
                _continuingLvlList.value = Resource.Success(dataContinuing)
            } catch (e: Exception) {
                _continuingLvlList.value = Resource.Error(e.message.toString())
            }
            Log.d("LoadDataApi", "LvlViewModel: ${continuingLvlList.value}")
        }
    }

    fun loadWorkoutListAdvanced() {
        viewModelScope.launch {
            try {
                _advancedLvlList.value = Resource.Loading
                val dataAdvanced = getWorkoutAdvancedInfo.invoke()
                _advancedLvlList.value = Resource.Success(dataAdvanced)
            } catch (e: Exception) {
                _advancedLvlList.value = Resource.Error(e.message.toString())
            }
            Log.d("LoadDataApi", "LvlViewModel: ${advancedLvlList.value}")
        }
    }
}