package com.android.habibi.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.android.habibi.core.domain.usecase.IMovieUseCase

class FavoriteViewModel constructor(
    movieUseCase: IMovieUseCase
) : ViewModel() {
    val listFavoriteMovie = movieUseCase.getAllFavoriteMovie().asLiveData()
}