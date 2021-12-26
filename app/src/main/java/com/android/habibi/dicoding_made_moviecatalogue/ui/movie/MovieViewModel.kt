package com.android.habibi.dicoding_made_moviecatalogue.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.android.habibi.core.data.Resource
import com.android.habibi.core.domain.model.Movie
import com.android.habibi.core.domain.usecase.IMovieUseCase

class MovieViewModel constructor(
    private val movieUseCase: IMovieUseCase
) : ViewModel() {

    fun getMovie(): LiveData<Resource<List<Movie>>> =
        movieUseCase.getAllMovie().asLiveData()
}