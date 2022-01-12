package com.android.habibi.dicoding_made_moviecatalogue.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.navArgs
import com.android.habibi.core.ui.DataResource
import com.android.habibi.core.ui.model.MovieDetail
import com.android.habibi.core.utils.setImage
import com.android.habibi.dicoding_made_moviecatalogue.R
import com.android.habibi.dicoding_made_moviecatalogue.databinding.ActivityDetailMovieBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import org.koin.android.viewmodel.ext.android.viewModel

class DetailMovieActivity : AppCompatActivity() {

    private val viewModel: DetailMovieViewModel by viewModel()

    private lateinit var binding: ActivityDetailMovieBinding

    private var isFavorite: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        val args: DetailMovieActivityArgs by navArgs()
        startObserverData(args.movieId.toString())
        startObserverFavorite(args.movieId.toString())
    }

    private fun startListener(movie: MovieDetail){
        binding.fabFavorite.setOnClickListener {
            if (isFavorite != null)
                if (isFavorite == false)
                    viewModel.setFavoriteMovie(movie)
                else
                    viewModel.deleteFavoriteMovie(movie)
        }
    }

    private fun startErrorListener(){
        binding.viewError.btnTryAgain.setOnClickListener {
            viewModel.refreshData()
        }
    }

    private fun startObserverFavorite(movieId: String){
        viewModel.isFavoriteMovie(movieId).observe(this, {
            binding.fabFavorite.imageTintList =
                if (it != null) {
                    isFavorite = true
                    getColorStateList(R.color.is_favorite)
                } else {
                    isFavorite = false
                    getColorStateList(R.color.is_not_favorite)
                }
        })
    }

    private fun startObserverData(movieId: String){
        viewModel.getData(movieId).observe(this, {
            if (it != null){
                when (it){
                    is DataResource.Loading -> {
                        onLoading()
                    }
                    is DataResource.Success -> {
                        val movie = it.data!!
                        startListener(movie)
                        setData(movie)
                        onSuccess()
                    }
                    is DataResource.Error -> {
                        onError()
                    }
                    is DataResource.Empty -> {
                        onEmpty()
                    }
                }
            }
        })
    }

    private fun onLoading(){
        binding.apply {
            progressBar.visibility = View.VISIBLE
            viewContent.visibility = View.GONE
            fabFavorite.visibility = View.GONE
            viewError.root.visibility = View.GONE
            viewDataEmpty.root.visibility = View.GONE
        }
    }

    private fun onSuccess(){
        binding.apply {
            progressBar.visibility = View.GONE
            viewContent.visibility = View.VISIBLE
            fabFavorite.visibility = View.VISIBLE
            viewError.root.visibility = View.GONE
            viewDataEmpty.root.visibility = View.GONE
        }
    }

    private fun onError(){
        binding.apply {
            progressBar.visibility = View.GONE
            viewContent.visibility = View.GONE
            fabFavorite.visibility = View.GONE
            viewError.root.visibility = View.VISIBLE
            viewDataEmpty.root.visibility = View.GONE
        }

        startErrorListener()
    }

    private fun onEmpty(){
        binding.apply {
            progressBar.visibility = View.GONE
            viewContent.visibility = View.GONE
            fabFavorite.visibility = View.GONE
            viewError.root.visibility = View.GONE
            viewDataEmpty.root.visibility = View.VISIBLE
        }
    }

    private fun setData(movie: MovieDetail){
        binding.apply {
            setImage(ivBackdrop, movie.backdropPath)
            setImage(ivPoster, movie.posterPath)
            tvTitleMovie.text = movie.title
            tvOverview.text = movie.overview
            tvRating.text = movie.voteAverage.toString()
            tvVoteCount.text = "(".plus(movie.voteCount.toString()).plus(" ").plus(getString(R.string.vote)).plus(")")
            initChipGroup(cgGenre, movie.genres)

            val textHour =
                if (movie.hourRuntime > 0){
                    movie.hourRuntime.toString().plus(" ").plus(
                        if (movie.hourRuntime == 1)
                            getString(R.string.hour)
                        else
                            getString(R.string.hours)
                    )
                } else {
                    ""
                }

            val textMinute =
                if (movie.minuteRuntime > 0){
                    movie.minuteRuntime.toString().plus(" ").plus(
                        if (movie.minuteRuntime == 1)
                            getString(R.string.minute)
                        else
                            getString(R.string.minutes)
                    )
                } else {
                    ""
                }

            tvDuration.text = textHour.plus(" ").plus(textMinute)

            tvAdult.text = getString(
                if (movie.adult){
                    R.string.adult
                } else {
                    R.string.all_ages
                }
            )
        }
    }

    private fun initChipGroup(chipGroup: ChipGroup, list: List<String>) {
        chipGroup.removeAllViews()
        for (text in list) {
            val chip =
                layoutInflater.inflate(R.layout.item_chip, chipGroup, false) as Chip
            chip.text = text
            chipGroup.addView(chip)
        }
    }
}