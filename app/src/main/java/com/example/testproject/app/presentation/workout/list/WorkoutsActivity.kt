package com.example.testproject.app.presentation.workout.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import com.example.testproject.R
import com.example.testproject.app.presentation.settings.SettingsActivity
import com.example.testproject.databinding.ActivityWorkoutsBinding
import com.google.android.material.snackbar.Snackbar

class WorkoutsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkoutsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}