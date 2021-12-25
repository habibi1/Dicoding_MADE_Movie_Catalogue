package id.go.jakarta.dicoding_made_moviecatalogue.di

import id.go.jakarta.dicoding_made_moviecatalogue.core.domain.usecase.IMovieUseCase
import id.go.jakarta.dicoding_made_moviecatalogue.core.domain.usecase.MovieInteractor
import id.go.jakarta.dicoding_made_moviecatalogue.ui.favorite.FavoriteViewModel
import id.go.jakarta.dicoding_made_moviecatalogue.ui.movie.MovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<IMovieUseCase> { MovieInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
}