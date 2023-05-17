package com.example.testproject.app.presentation.workout.lvl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.app.domain.usecase.api.LoadDataUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class LvlViewModel @Inject constructor(
    private val loadDataUseCase: LoadDataUseCase
) : ViewModel() {
    init {
        viewModelScope.launch {
            loadDataUseCase.invoke()
        }
    }
}