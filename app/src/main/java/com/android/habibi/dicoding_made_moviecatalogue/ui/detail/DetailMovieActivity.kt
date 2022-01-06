package com.android.habibi.dicoding_made_moviecatalogue.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.navArgs
import com.android.habibi.core.data.Resource
import com.android.habibi.core.domain.model.MovieDetail
import com.android.habibi.core.utils.setImage
import com.android.habibi.dicoding_made_moviecatalogue.databinding.ActivityDetailMovieBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DetailMovieActivity : AppCompatActivity() {

    private val viewModel: DetailMovieViewModel by viewModel()

    private var _binding: ActivityDetailMovieBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                        onSuccess(movie)
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
            appBar.visibility = View.GONE
        }
    }

    private fun onSuccess(movie: MovieDetail){
        binding.apply {
            progressBar.visibility = View.GONE
            appBar.visibility = View.VISIBLE

            setImage(ivBackdrop, movie.backdropPath)
            setImage(ivPoster, movie.posterPath)
        }
    }

    private fun onError(){
        binding.apply {
            progressBar.visibility = View.GONE
            appBar.visibility = View.GONE
        }
    }
}