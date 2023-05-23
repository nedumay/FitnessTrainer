package com.example.testproject.app.presentation.workout.detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.testproject.app.common.Resource
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.presentation.factory.ViewModelFactory
import com.example.testproject.databinding.ActivityDetailBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }
    private val component by lazy {
        (application as App).component
    }
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if(!intent.hasExtra(EXTRA_FROM_ID)){
            finish()
            return
        }
        val idExercise = intent.getIntExtra(EXTRA_FROM_ID, 0)
        val idExerciseList = intent.getIntExtra(EXTRA_FROM_ID_LIST, 0)

        viewModel = ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]
        viewModel.loadExerciseDetail(idExercise = idExercise, idExercisesList = idExerciseList)
        viewModel.exerciseInfo.onEach {
            when(it){
                is Resource.Loading -> {
                    with(binding){
                        progressBar.visibility = View.VISIBLE
                        cardAreaVideo.visibility = View.GONE
                        textViewTitle.visibility = View.GONE
                        textViewSubtitle.visibility = View.GONE
                        textViewDescription.visibility = View.GONE
                        textViewAreaTitle.visibility = View.GONE
                        textViewAreaDescription.visibility = View.GONE
                        cardAreaImage.visibility = View.GONE
                    }
                }
                is Resource.Success -> {
                    with(binding){
                        progressBar.visibility = View.GONE
                        cardAreaVideo.visibility = View.VISIBLE
                        lifecycle.addObserver(youTubePlayerView)
                        youTubePlayerView.addYouTubePlayerListener(
                            object : AbstractYouTubePlayerListener() {
                                override fun onReady(youTubePlayer: YouTubePlayer) {
                                    youTubePlayer.loadVideo(it.data.video, 0f)
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
                        Glide.with(this@DetailActivity)
                            .load(it.data.areaImg)
                            .centerCrop()
                            .into(imageViewArea)
                    }
                }
                is Resource.Error -> {
                    with(binding){
                        progressBar.visibility = View.VISIBLE
                        cardAreaVideo.visibility = View.GONE
                        textViewTitle.visibility = View.GONE
                        textViewSubtitle.visibility = View.GONE
                        textViewDescription.visibility = View.GONE
                        textViewAreaTitle.visibility = View.GONE
                        textViewAreaDescription.visibility = View.GONE
                        cardAreaImage.visibility = View.GONE
                    }
                }
            }
        }.launchIn(lifecycleScope)
        binding.buttonClose.setOnClickListener {
            finish()
        }

    }

    companion object {

        private const val EXTRA_FROM_ID = "id"
        private const val EXTRA_FROM_ID_LIST = "id list"
        fun newIntent(context: Context, idExercise: Int, idExerciseList: Int): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_ID,idExercise)
            intent.putExtra(EXTRA_FROM_ID_LIST,idExerciseList)
            return intent
        }
    }
}