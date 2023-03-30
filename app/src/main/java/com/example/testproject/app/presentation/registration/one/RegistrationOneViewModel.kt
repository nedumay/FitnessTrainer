package com.example.testproject.app.presentation.registration.one

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegistrationOneViewModel : ViewModel() {

    private var _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private var _dataOfBirth = MutableLiveData<String>()
    val dataOfBirth: LiveData<String>
        get() = _dataOfBirth

    fun save(name: String, date: String) {
        _name.value = name
        _dataOfBirth.value = date
    }

}