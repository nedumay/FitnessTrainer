package com.example.testproject.app.presentation.workout.lvl

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.testproject.R
import com.example.testproject.app.common.Resource
import com.example.testproject.app.domain.model.beginner.Workout
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.presentation.dashboard.DashboardActivity
import com.example.testproject.app.presentation.factory.ViewModelFactory
import com.example.testproject.app.presentation.workout.list.ExercisesActivity
import com.example.testproject.app.presentation.workout.lvl.adapters.LvlWorkoutAdapter
import com.example.testproject.databinding.ActivityLvlBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class LvlActivity : AppCompatActivity() {

    private lateinit var viewModel: LvlViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val lvlWorkoutBeginnerAdapter by lazy {
        LvlWorkoutAdapter()
    }
    private val lvlWorkoutContinuingAdapter by lazy {
        LvlWorkoutAdapter()
    }

    private val  binding by lazy {
        ActivityLvlBinding.inflate(layoutInflater)
    }

    private val component by lazy {
        (application as App).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, viewModelFactory)[LvlViewModel::class.java]

        appBarMenu()

        viewModel.loadWorkoutListBeginner()
        viewModel.beginnerLvlList.onEach { beginner ->
            when (beginner) {
                is Resource.Loading -> {
                    binding.recyclerViewBeginners.visibility = View.GONE
                    binding.textViewBeginner.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    initAdapter(beginner, lvlWorkoutBeginnerAdapter, binding.recyclerViewBeginners)
                    binding.progressBar.visibility = View.GONE
                    binding.textViewBeginner.visibility = View.VISIBLE
                    binding.recyclerViewBeginners.visibility = View.VISIBLE
                    lvlWorkoutBeginnerAdapter.onWorkoutClickListener = {
                        val intent = ExercisesActivity.newIntent(this@LvlActivity, it.id, it.title, it.picture)
                        startActivity(intent)
                    }
                }
                is Resource.Error -> {
                    binding.textViewBeginner.visibility = View.GONE
                    binding.recyclerViewBeginners.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this@LvlActivity, beginner.message, Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }.launchIn(lifecycleScope)

        viewModel.loadWorkoutListContinuing()
        viewModel.continuingLvlList.onEach { continuing ->
            when (continuing) {
                is Resource.Loading -> {
                    binding.recyclerViewContinuing.visibility = View.GONE
                    binding.textViewContinuing.visibility = View.GONE
                }
                is Resource.Success -> {
                    initAdapter(continuing, lvlWorkoutContinuingAdapter, binding.recyclerViewContinuing)
                    binding.textViewContinuing.visibility = View.VISIBLE
                    binding.recyclerViewContinuing.visibility = View.VISIBLE
                    lvlWorkoutContinuingAdapter.onWorkoutClickListener = {
                        val intent = ExercisesActivity.newIntent(this@LvlActivity, it.id, it.title, it.picture)
                        startActivity(intent)
                    }
                }
                is Resource.Error -> {
                    binding.textViewContinuing.visibility = View.GONE
                    binding.recyclerViewContinuing.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this@LvlActivity, continuing.message, Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }.launchIn(lifecycleScope)

    }

    private fun initAdapter(it: Resource.Success<List<Workout>>, adapterLvl: LvlWorkoutAdapter, recyclerView: RecyclerView) {
        recyclerView.adapter = adapterLvl
        adapterLvl.submitList(it.data)
    }

    private fun appBarMenu() {

        binding.appBarLayout.setOnClickListener { view ->
            Snackbar.make(view, "Replace with you own action", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show()
        }

        binding.topAppBarWorkout.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeDashboard -> {
                    val intent = Intent(this@LvlActivity, DashboardActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_workout, menu)
        return true
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, LvlActivity::class.java)
        }
    }
}