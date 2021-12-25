package id.go.jakarta.dicoding_made_moviecatalogue.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.go.jakarta.dicoding_made_moviecatalogue.core.data.Resource
import id.go.jakarta.dicoding_made_moviecatalogue.core.domain.model.Movie
import id.go.jakarta.dicoding_made_moviecatalogue.core.domain.usecase.IMovieUseCase

class MovieViewModel constructor(
    private val movieUseCase: IMovieUseCase
) : ViewModel() {

    fun getMovie(): LiveData<Resource<List<Movie>>> =
        movieUseCase.getAllMovie().asLiveData()
}