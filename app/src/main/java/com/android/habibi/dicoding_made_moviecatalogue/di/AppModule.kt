package com.android.habibi.dicoding_made_moviecatalogue.di

import com.android.habibi.core.domain.usecase.IMovieUseCase
import com.android.habibi.core.domain.usecase.MovieInteractor
import com.android.habibi.dicoding_made_moviecatalogue.ui.favorite.FavoriteViewModel
import com.android.habibi.dicoding_made_moviecatalogue.ui.movie.MovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<IMovieUseCase> { MovieInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
}