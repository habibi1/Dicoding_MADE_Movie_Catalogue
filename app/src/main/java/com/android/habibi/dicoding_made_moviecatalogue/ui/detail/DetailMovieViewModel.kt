package com.android.habibi.dicoding_made_moviecatalogue.ui.detail

import androidx.lifecycle.*
import com.android.habibi.core.data.Resource
import com.android.habibi.core.domain.model.Movie
import com.android.habibi.core.domain.model.MovieDetail
import com.android.habibi.core.domain.usecase.IMovieUseCase
import kotlinx.coroutines.launch

class DetailMovieViewModel constructor(
    private val movieUseCase: IMovieUseCase
): ViewModel() {
    private val loadTrigger = MutableLiveData(Unit)

    fun refreshData(){
        loadTrigger.value = Unit
    }

    fun getData(movieId: String): LiveData<Resource<MovieDetail>> = loadTrigger.switchMap {
        movieUseCase.getDetailMovie(movieId).asLiveData()
    }

    fun isFavoriteMovie(movieId: String) =
        movieUseCase.isFavoriteMovie(movieId).asLiveData()

    fun setFavoriteMovie(movie: MovieDetail) = viewModelScope.launch {
        movieUseCase.insertFavoriteMovie(movie)
    }

    fun deleteFavoriteMovie(movie: MovieDetail) = viewModelScope.launch {
        movieUseCase.deleteFavoriteMovie(movie)
    }
}