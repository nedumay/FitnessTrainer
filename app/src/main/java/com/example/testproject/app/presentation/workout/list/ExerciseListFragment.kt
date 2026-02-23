package com.example.testproject.app.presentation.workout.list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.testproject.R
import com.example.testproject.app.common.Resource
import com.example.testproject.app.domain.model.beginner.Exercise
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.presentation.factory.ViewModelFactory
import com.example.testproject.app.presentation.workout.list.adapters.ExerciseAdapter
import com.example.testproject.databinding.FragmentExerciseListBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


class ExerciseListFragment : Fragment() {

    private var _binding: FragmentExerciseListBinding? = null
    private val binding: FragmentExerciseListBinding
        get() = _binding ?: throw RuntimeException("FragmentExerciseListBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ExercisesViewModel::class.java]
    }

    private lateinit var exerciseAdapter: ExerciseAdapter

    private var id: Int? = null
    private var title: String? = null
    private var picture: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).component.inject(this@ExerciseListFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(GET_PUT_ID_KEY)
            title = it.getString(GET_PUT_TITLE_KEY)
            picture = it.getString(GET_PUT_PICTURE_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExerciseListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("ExerciseListFragmentLoad", "$id $title $picture")
        if (id == null && title == null && picture == null) {
            launchLevelFragment()
        } else {
            viewModel.loadExerciseList(id!!)
            viewModel.exerciseInfoList.onEach {
                when (it) {
                    is Resource.Loading -> {
                        binding.nestedScrollView.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        initAdapter(it)
                        binding.textViewCountWorkout.text = it.data.size.toString()
                        binding.textViewWorkout.text = title

                        val imagePath = picture
                        val fullUrl = "file:///android_asset/$imagePath"

                        Glide.with(this)
                            .load(fullUrl)
                            .centerCrop()
                            .into(binding.imageViewWorkout)
                        if (id!! < 6) {
                            binding.ratingBarWorkout.rating = 1.0f
                        } else if (id in 6..10) {
                            binding.ratingBarWorkout.rating = 2.0f
                        } else {
                            binding.ratingBarWorkout.rating = 3.0f
                        }
                        binding.nestedScrollView.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        exerciseAdapter.onExerciseClickListener = {
                            launchDetailFragment(it.id, id!!)
                        }
                    }
                    is Resource.Error -> {
                        binding.nestedScrollView.visibility = View.GONE
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }.launchIn(lifecycleScope)
        }

        binding.imageViewArrowBack.setOnClickListener {
            launchLevelFragment()
        }

        onBackFragment()
    }

    private fun onBackFragment() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    launchLevelFragment()
                }
            })
    }

    private fun launchLevelFragment() {
        findNavController().navigate(R.id.action_exerciseListFragment_to_levelFragment)
    }

    private fun launchDetailFragment(idExercise: Int, idExerciseList: Int) {
        val bundle = Bundle()
        bundle.putInt(PUT_ID_EXERCISE_KEY, idExercise)
        bundle.putInt(PUT_ID_EXERCISE_LIST_KEY, idExerciseList)
        bundle.putInt(GET_PUT_ID_KEY, id!!)
        bundle.putString(GET_PUT_TITLE_KEY, title)
        bundle.putString(GET_PUT_PICTURE_KEY, picture)
        findNavController().navigate(R.id.action_exerciseListFragment_to_detailFragment, bundle)
    }


    private fun initAdapter(it: Resource.Success<List<Exercise>>) {
        exerciseAdapter = ExerciseAdapter()
        binding.recyclerViewExercise.adapter = exerciseAdapter
        exerciseAdapter.submitList(it.data)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val GET_PUT_ID_KEY = "id"
        private const val GET_PUT_TITLE_KEY = "title"
        private const val GET_PUT_PICTURE_KEY = "picture"
        private const val PUT_ID_EXERCISE_KEY = "id_exercise"
        private const val PUT_ID_EXERCISE_LIST_KEY = "id_exercise_list"
    }


}