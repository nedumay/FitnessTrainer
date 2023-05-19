package com.example.testproject.app.presentation.workout.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.testproject.app.common.Resource
import com.example.testproject.app.domain.model.beginner.Exercise
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.presentation.factory.ViewModelFactory
import com.example.testproject.app.presentation.workout.list.adapters.ExerciseAdapter
import com.example.testproject.app.presentation.workout.lvl.LvlActivity
import com.example.testproject.app.presentation.workout.lvl.adapters.LvlWorkoutAdapter
import com.example.testproject.databinding.ActivityExercisesBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ExercisesActivity : AppCompatActivity() {

    private lateinit var viewModel: ExercisesViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var exerciseAdapter: ExerciseAdapter

    private val binding by lazy {
        ActivityExercisesBinding.inflate(layoutInflater)
    }

    private val component by lazy {
        (application as App).component
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if(!intent.hasExtra(EXTRA_FROM_ID)){
            finish()
            return
        }
        val idExercise = intent.getIntExtra(EXTRA_FROM_ID, 0)
        val titleExercise = intent.getStringExtra(EXTRA_TITLE)
        val pictureExercise = intent.getStringExtra(EXTRA_PICTURE)

        viewModel = ViewModelProvider(this, viewModelFactory)[ExercisesViewModel::class.java]
        viewModel.loadExerciseList(idExercise)
        viewModel.exerciseInfo.onEach {
            when (it) {
                is Resource.Loading -> {
                    binding.nestedScrollView.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    initAdapter(it)
                    binding.textViewCountWorkout.text = it.data.size.toString()
                    binding.textViewWorkout.text = titleExercise
                    Glide.with(this)
                        .load(pictureExercise)
                        .centerCrop()
                        .into(binding.imageViewWorkout)
                    binding.ratingBarWorkout.rating = 1.0f
                    binding.nestedScrollView.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
                is Resource.Error -> {
                    binding.nestedScrollView.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                }
            }
        }.launchIn(lifecycleScope)

        binding.imageViewArrowBack.setOnClickListener {
            startActivity(LvlActivity.newIntent(this@ExercisesActivity))
            finish()
        }
    }

    private fun initAdapter(it: Resource.Success<List<Exercise>>) {
        exerciseAdapter = ExerciseAdapter()
        binding.recyclerViewExercise.adapter = exerciseAdapter
        exerciseAdapter.submitList(it.data)
    }

    companion object {

        private const val EXTRA_FROM_ID = "id"
        private const val EXTRA_TITLE = "name"
        private const val EXTRA_PICTURE = "picture"
        fun newIntent(context: Context, idExercise: Int, title: String, picture: String): Intent {
            val intent = Intent(context, ExercisesActivity::class.java)
            intent.putExtra(EXTRA_FROM_ID,idExercise)
            intent.putExtra(EXTRA_TITLE, title)
            intent.putExtra(EXTRA_PICTURE, picture)
            return intent
        }
    }

}