package com.android.habibi.dicoding_made_moviecatalogue.ui.detail

import androidx.lifecycle.*
import com.android.habibi.core.data.Resource
import com.android.habibi.core.ui.model.MovieDetail as MovieDetailPresentation
import com.android.habibi.core.domain.usecase.IMovieUseCase
import com.android.habibi.core.ui.DataResource
import com.android.habibi.core.ui.utils.DataMapper.mapMovieDetailToPresentation
import com.android.habibi.core.ui.utils.DataMapper.mapMoviePresentationToDomain
import kotlinx.coroutines.launch

class DetailMovieViewModel constructor(
    private val movieUseCase: IMovieUseCase
): ViewModel() {
    private val loadTrigger = MutableLiveData(Unit)

    fun refreshData(){
        loadTrigger.value = Unit
    }

    fun getData(movieId: String): LiveData<DataResource<MovieDetailPresentation>> = loadTrigger.switchMap {
        Transformations.map(movieUseCase.getDetailMovie(movieId).asLiveData()){
            when(it){
                is Resource.Loading -> {
                    DataResource.Loading()
                }
                is Resource.Success -> {
                    if (it.data != null){
                        DataResource.Success(
                            mapMovieDetailToPresentation(it.data!!)
                        )
                    } else {
                        DataResource.Empty()
                    }
                }
                is Resource.Error -> {
                    DataResource.Error(it.message.toString())
                }
            }
        }
    }

    fun isFavoriteMovie(movieId: String) =
        movieUseCase.isFavoriteMovie(movieId).asLiveData()

    fun setFavoriteMovie(movie: MovieDetailPresentation) = viewModelScope.launch {
        movieUseCase.insertFavoriteMovie(
            mapMoviePresentationToDomain(movie)
        )
    }

    fun deleteFavoriteMovie(movie: MovieDetailPresentation) = viewModelScope.launch {
        movieUseCase.deleteFavoriteMovie(
            mapMoviePresentationToDomain(movie)
        )
    }
}