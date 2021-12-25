package id.go.jakarta.dicoding_made_moviecatalogue.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.go.jakarta.dicoding_made_moviecatalogue.core.domain.model.Movie
import id.go.jakarta.dicoding_made_moviecatalogue.core.domain.usecase.IMovieUseCase

class FavoriteViewModel constructor(
    private val movieUseCase: IMovieUseCase
) : ViewModel() {

    fun getFavoriteMovie(): LiveData<List<Movie>> =
        movieUseCase.getFavoriteMovie().asLiveData()
}