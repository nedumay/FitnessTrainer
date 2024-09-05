package com.example.testproject.app.presentation.workout.detail

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
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
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.presentation.factory.ViewModelFactory
import com.example.testproject.databinding.FragmentDetailBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding
        get() = _binding ?: throw RuntimeException("FragmentDetailBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]
    }

    private var idExercise: Int? = null
    private var idExerciseList: Int? = null
    private var id: Int? = null
    private var title: String? = null
    private var picture: String? = null


    private var countDownTimer: CountDownTimer? = null
    private var timeInMillis: Long = 0
    private lateinit var sound: MediaPlayer
    private var timeRemaining: Long = 0
    private var isTimerRunning = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).component.inject(this@DetailFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idExercise = it.getInt(GET_ID_EXERCISE_KEY)
            idExerciseList = it.getInt(GET_ID_EXERCISE_LIST_KEY)
            id = it.getInt(GET_PUT_ID_KEY)
            title = it.getString(GET_PUT_TITLE_KEY)
            picture = it.getString(GET_PUT_PICTURE_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("ExerciseListFragmentLoad", "$id $title $picture")
        if (idExercise == null || idExerciseList == null) {
            launchExerciseListFragment()
        } else {
            ExerciseListObserve()
        }

        binding.buttonClose.setOnClickListener {
            launchExerciseListFragment()
        }

        onBackFragment()
    }

    private fun onBackFragment() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    launchExerciseListFragment()
                }
            })
    }

    private fun ExerciseListObserve() {
        viewModel.loadExerciseDetail(idExercise = idExercise!!, idExercisesList = idExerciseList!!)
        viewModel.exerciseInfo.onEach {
            when (it) {
                is Resource.Loading -> {
                    with(binding) {
                        progressBar.visibility = View.VISIBLE
                        cardAreaVideo.visibility = View.GONE
                        textViewTitle.visibility = View.GONE
                        textViewSubtitle.visibility = View.GONE
                        textViewDescription.visibility = View.GONE
                        textViewAreaTitle.visibility = View.GONE
                        textViewAreaDescription.visibility = View.GONE
                        cardAreaImage.visibility = View.GONE
                        buttonStart.visibility = View.GONE
                        buttonPause.visibility = View.GONE
                    }
                }

                is Resource.Success -> {
                    with(binding) {
                        progressBar.visibility = View.GONE
                        cardAreaVideo.visibility = View.VISIBLE
                        lifecycle.addObserver(youTubePlayerView)
                        youTubePlayerView.addYouTubePlayerListener(
                            object : AbstractYouTubePlayerListener() {
                                override fun onReady(youTubePlayer: YouTubePlayer) {
                                    youTubePlayer.loadVideo(it.data.video, 0f)
                                    // Возможно нужно будет исправить, чтобы автоматом сразу не включал видео
                                    youTubePlayer.play()
                                }
                            }
                        )
                        textViewTitle.visibility = View.VISIBLE
                        textViewTitle.text = it.data.name
                        textViewSubtitle.visibility = View.VISIBLE
                        textViewSubtitle.text = it.data.subtitle
                        textViewDescription.visibility = View.VISIBLE
                        textViewDescription.text = it.data.desc
                        textViewAreaTitle.visibility = View.VISIBLE
                        textViewAreaDescription.visibility = View.VISIBLE
                        textViewAreaDescription.text = it.data.area
                        cardAreaImage.visibility = View.VISIBLE

                        Glide.with(requireContext())
                            .load(it.data.areaImg)
                            .centerCrop()
                            .into(imageViewArea)

                        // Создадим звук для уведомления о начале и конце тренировки
                        val timeString = it.data.subtitle
                        if (timeString[0] == '0') {
                            sound = MediaPlayer.create(requireContext(), R.raw.svistok_trenera)
                            buttonStart.visibility = View.VISIBLE
                            buttonPause.visibility = View.VISIBLE
                            buttonPause.isEnabled = false
                            // Клик на кнопку для запуска таймера
                            buttonStart.setOnClickListener {
                                if (!isTimerRunning) {
                                    timeInMillis = convertTimeToMillis(timeString)
                                    sound.start()
                                    startTimer(timeInMillis)
                                }
                            }
                            buttonPause.setOnClickListener {
                                if (isTimerRunning) {
                                    stopTimer()
                                    buttonPause.text = getString(R.string.continue_exc)
                                } else{
                                    startTimer(timeRemaining)
                                    buttonPause.text = getString(R.string.pause_exc)
                                }
                            }
                        }

                        buttonPause.setOnClickListener {
                            if (isTimerRunning) {
                                stopTimer()
                                buttonPause.text = getString(R.string.continue_exc)
                            } else {
                                startTimer(timeRemaining)
                                buttonPause.text = getString(R.string.pause_exc)
                            }
                        }
                    }
                }


                is Resource.Error -> {
                    with(binding) {
                        progressBar.visibility = View.VISIBLE
                        cardAreaVideo.visibility = View.GONE
                        textViewTitle.visibility = View.GONE
                        textViewSubtitle.visibility = View.GONE
                        textViewDescription.visibility = View.GONE
                        textViewAreaTitle.visibility = View.GONE
                        textViewAreaDescription.visibility = View.GONE
                        cardAreaImage.visibility = View.GONE
                        buttonStart.visibility = View.GONE
                        buttonPause.visibility = View.GONE
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }

            }

        }.launchIn(lifecycleScope)
    }

    private fun launchExerciseListFragment() {
        val bundle = Bundle()
        bundle.putInt(GET_PUT_ID_KEY, id!!)
        bundle.putString(GET_PUT_TITLE_KEY, title)
        bundle.putString(GET_PUT_PICTURE_KEY, picture)
        findNavController().navigate(R.id.action_detailFragment_to_exerciseListFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
        _binding = null
    }

    // Запуск таймера
    private fun startTimer(timeInMillis: Long) {
        countDownTimer?.cancel() // Если таймер уже работает, отменить его

        countDownTimer = object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining = millisUntilFinished
                binding.textViewSubtitle.text = formatTime(millisUntilFinished)
                binding.buttonStart.isEnabled = false
                binding.buttonPause.isEnabled = true
            }

            override fun onFinish() {
                binding.textViewSubtitle.text = "00:00"
                isTimerRunning = false
                sound.start()
                binding.buttonStart.isEnabled = true
                binding.buttonPause.isEnabled = false
            }
        }.start()

        isTimerRunning = true
    }

    private fun stopTimer() {
        countDownTimer?.cancel() // Остановить таймер
        isTimerRunning = false
    }

    private fun convertTimeToMillis(timeString: String): Long {
        val parts = timeString.split(":")
        val minutes = parts[0].toInt()
        val seconds = parts[1].toInt()
        return (minutes * 60 + seconds) * 1000L
    }

    @SuppressLint("DefaultLocale")
    private fun formatTime(millis: Long): String {
        val totalSeconds = millis / 1000
        val minutes = (totalSeconds / 60).toInt()
        val seconds = (totalSeconds % 60).toInt()
        return String.format("%02d:%02d", minutes, seconds)
    }

    companion object {
        private const val GET_PUT_ID_KEY = "id"
        private const val GET_PUT_TITLE_KEY = "title"
        private const val GET_PUT_PICTURE_KEY = "picture"
        private const val GET_ID_EXERCISE_KEY = "id_exercise"
        private const val GET_ID_EXERCISE_LIST_KEY = "id_exercise_list"
    }

}