package com.example.testproject.app.presentation.registration.three

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegistrationThreeViewModel : ViewModel() {

    private var _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private var _dataOfBirth = MutableLiveData<String>()
    val dataOfBirth: LiveData<String>
        get() = _dataOfBirth

    private var _gender = MutableLiveData<Boolean>()
    val gender: LiveData<Boolean>
        get() = _gender

    private var _weight = MutableLiveData<String>()
    val weight_: LiveData<String>
        get() = _weight

    private var _height = MutableLiveData<String>()
    val height_: LiveData<String>
        get() = _height

    private var _targetWeight = MutableLiveData<String>()
    val targetWeight: LiveData<String>
        get() = _targetWeight

    fun save(
        name: String,
        date: String,
        gender: Boolean,
        weight: String,
        height: String,
        targetWeight: String
    ) {
        _name.value = name
        _dataOfBirth.value = date
        _gender.value = gender
        _weight.value = weight
        _height.value = height
        _targetWeight.value = targetWeight
    }
}