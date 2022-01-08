package com.android.habibi.dicoding_made_moviecatalogue.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.navArgs
import com.android.habibi.core.data.Resource
import com.android.habibi.core.domain.model.MovieDetail
//import com.android.habibi.core.ui.model.MovieDetail
import com.android.habibi.core.utils.setImage
import com.android.habibi.dicoding_made_moviecatalogue.R
import com.android.habibi.dicoding_made_moviecatalogue.databinding.ActivityDetailMovieBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import org.koin.android.viewmodel.ext.android.viewModel

class DetailMovieActivity : AppCompatActivity() {

    private val viewModel: DetailMovieViewModel by viewModel()

    private var _binding: ActivityDetailMovieBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        val args: DetailMovieActivityArgs by navArgs()
        startObserver(args.movieId.toString())
        viewModel.isFavoriteMovie(args.movieId.toString()).observe(this, {
            binding.cbFavorite.isChecked = it != null
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun startListener(movie: MovieDetail){
        binding.cbFavorite.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                viewModel.setFavoriteMovie(movie)
            else
                viewModel.deleteFavoriteMovie(movie)
        }
    }

    private fun startObserver(movieId: String){
        viewModel.getData(movieId).observe(this, {
            if (it != null){
                when (it){
                    is Resource.Loading -> {
                        onLoading()
                    }
                    is Resource.Success -> {
                        val movie = it.data!!
                        startListener(movie)
                        setData(movie)
                        onSuccess()
                    }
                    is Resource.Error -> {
                        onError()
                    }
                }
            }
        })
    }

    private fun onLoading(){
        binding.apply {
            progressBar.visibility = View.VISIBLE
            ivBackdrop.visibility = View.GONE
            viewGradient.visibility = View.GONE
            viewContent1.visibility = View.GONE
            tvTitleGenre.visibility = View.GONE
            cgGenre.visibility = View.GONE
            tvTitleOverview.visibility = View.GONE
            tvOverview.visibility = View.GONE
            ivBack.visibility = View.GONE
            cbFavorite.visibility = View.GONE
        }
    }

    private fun onSuccess(){
        binding.apply {
            progressBar.visibility = View.GONE
            ivBackdrop.visibility = View.VISIBLE
            viewGradient.visibility = View.VISIBLE
            viewContent1.visibility = View.VISIBLE
            tvTitleGenre.visibility = View.VISIBLE
            cgGenre.visibility = View.VISIBLE
            tvTitleOverview.visibility = View.VISIBLE
            tvOverview.visibility = View.VISIBLE
            ivBack.visibility = View.VISIBLE
            cbFavorite.visibility = View.VISIBLE
        }
    }

    private fun onError(){
        binding.apply {
            progressBar.visibility = View.GONE
            ivBackdrop.visibility = View.GONE
            viewGradient.visibility = View.GONE
            viewContent1.visibility = View.GONE
            tvTitleGenre.visibility = View.GONE
            cgGenre.visibility = View.GONE
            tvTitleOverview.visibility = View.GONE
            tvOverview.visibility = View.GONE
            ivBack.visibility = View.GONE
            cbFavorite.visibility = View.GONE
        }
    }

    private fun setData(movie: MovieDetail){
        binding.apply {
            setImage(ivBackdrop, movie.backdropPath)
            setImage(ivPoster, movie.posterPath)
            tvTitleMovie.text = movie.title
            tvOverview.text = movie.overview
            tvRating.text = movie.voteAverage.toString()
            tvVoteCount.text = "(" + movie.voteCount.toString() + " " + getString(R.string.vote) + ")"
            initChipGroup(cgGenre, movie.genres)

            tvAdult.text = getString(
                if (movie.adult){
                    R.string.adult
                } else
                    R.string.all_ages
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