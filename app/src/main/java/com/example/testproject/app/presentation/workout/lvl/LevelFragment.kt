package com.example.testproject.app.presentation.workout.lvl

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.testproject.R
import com.example.testproject.app.common.Resource
import com.example.testproject.app.domain.model.beginner.Workout
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.presentation.factory.ViewModelFactory
import com.example.testproject.app.presentation.workout.lvl.adapters.LvlWorkoutAdapter
import com.example.testproject.databinding.FragmentLevelBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


class LevelFragment : Fragment() {

    private var _binding: FragmentLevelBinding? = null
    private val binding: FragmentLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentLevelBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[LevelViewModel::class.java]
    }

    private val lvlWorkoutBeginnerAdapter by lazy {
        LvlWorkoutAdapter()
    }
    private val lvlWorkoutContinuingAdapter by lazy {
        LvlWorkoutAdapter()
    }

    private val lvlWorkoutAdvancedAdapter by lazy {
        LvlWorkoutAdapter()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).component.inject(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appBarMenu()
        BeginnerObserver()
        ContinuingObserver()
        AdvancedObserver()
    }

    private fun BeginnerObserver() {
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
                        launchExercisesListFragment(
                            id = it.id,
                            title = it.title,
                            picture = it.picture
                        )
                    }
                }

                is Resource.Error -> {
                    binding.textViewBeginner.visibility = View.GONE
                    binding.recyclerViewBeginners.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), beginner.message, Toast.LENGTH_SHORT).show()
                    //launchDashboardFragment()
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun ContinuingObserver() {
        viewModel.loadWorkoutListContinuing()
        viewModel.continuingLvlList.onEach { continuing ->
            when (continuing) {
                is Resource.Loading -> {
                    binding.recyclerViewContinuing.visibility = View.GONE
                    binding.textViewContinuing.visibility = View.GONE
                }

                is Resource.Success -> {
                    initAdapter(
                        continuing,
                        lvlWorkoutContinuingAdapter,
                        binding.recyclerViewContinuing
                    )
                    binding.textViewContinuing.visibility = View.VISIBLE
                    binding.recyclerViewContinuing.visibility = View.VISIBLE
                    lvlWorkoutContinuingAdapter.onWorkoutClickListener = {
                        launchExercisesListFragment(
                            id = it.id,
                            title = it.title,
                            picture = it.picture
                        )
                    }
                }

                is Resource.Error -> {
                    binding.textViewContinuing.visibility = View.GONE
                    binding.recyclerViewContinuing.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), continuing.message, Toast.LENGTH_SHORT).show()
                    //launchDashboardFragment()
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun AdvancedObserver() {
        viewModel.loadWorkoutListAdvanced()
        viewModel.advancedLvlList.onEach { advanced ->
            when (advanced) {
                is Resource.Loading -> {
                    binding.recyclerViewAdvanced.visibility = View.GONE
                    binding.textViewAdvanced.visibility = View.GONE
                }

                is Resource.Success -> {
                    initAdapter(advanced, lvlWorkoutAdvancedAdapter, binding.recyclerViewAdvanced)
                    binding.textViewAdvanced.visibility = View.VISIBLE
                    binding.recyclerViewAdvanced.visibility = View.VISIBLE
                    lvlWorkoutAdvancedAdapter.onWorkoutClickListener = {
                        launchExercisesListFragment(
                            id = it.id,
                            title = it.title,
                            picture = it.picture
                        )
                    }
                }

                is Resource.Error -> {
                    binding.textViewAdvanced.visibility = View.GONE
                    binding.recyclerViewAdvanced.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), advanced.message, Toast.LENGTH_SHORT).show()
                    //launchDashboardFragment()
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun launchExercisesListFragment(id: Int, title: String, picture: String) {
        val bundle = Bundle()
        bundle.putInt(PUT_ID_KEY, id)
        bundle.putString(PUT_TITLE_KEY, title)
        bundle.putString(PUT_PICTURE_KEY, picture)
        findNavController().navigate(R.id.action_levelFragment_to_exerciseListFragment, bundle)
    }

    private fun launchDashboardFragment() {
        findNavController().navigate(R.id.action_levelFragment_to_dashboardFragment)
    }


    private fun initAdapter(
        it: Resource.Success<List<Workout>>,
        adapterLvl: LvlWorkoutAdapter,
        recyclerView: RecyclerView
    ) {
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
                    launchDashboardFragment()
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_workout, menu)
    }

    companion object {
        private const val PUT_ID_KEY = "id"
        private const val PUT_TITLE_KEY = "title"
        private const val PUT_PICTURE_KEY = "picture"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}