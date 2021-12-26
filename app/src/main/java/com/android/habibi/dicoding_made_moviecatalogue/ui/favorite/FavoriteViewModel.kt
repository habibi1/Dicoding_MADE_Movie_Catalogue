package com.android.habibi.dicoding_made_moviecatalogue.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.android.habibi.core.domain.model.Movie
import com.android.habibi.core.domain.usecase.IMovieUseCase

class FavoriteViewModel constructor(
    private val movieUseCase: IMovieUseCase
) : ViewModel() {

    fun getFavoriteMovie(): LiveData<List<Movie>> =
        movieUseCase.getFavoriteMovie().asLiveData()
}