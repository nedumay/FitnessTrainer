package com.example.testproject.app.presentation.registration.two

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegistrationTwoViewModel : ViewModel() {

    private var _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private var _dataOfBirth = MutableLiveData<String>()
    val dataOfBirth: LiveData<String>
        get() = _dataOfBirth

    private var _gender = MutableLiveData<Boolean>()
    val gender: LiveData<Boolean>
        get() = _gender

    fun save(name: String, date: String, gender: Boolean) {
        _name.value = name
        _dataOfBirth.value = date
        _gender.value = gender
    }

}