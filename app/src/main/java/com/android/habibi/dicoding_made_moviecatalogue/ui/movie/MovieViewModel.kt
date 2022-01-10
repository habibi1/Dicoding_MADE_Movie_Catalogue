package com.android.habibi.dicoding_made_moviecatalogue.ui.movie

import androidx.lifecycle.*
import com.android.habibi.core.data.Resource
import com.android.habibi.core.ui.model.Movie as MoviePresentation
import com.android.habibi.core.domain.usecase.IMovieUseCase
import com.android.habibi.core.ui.DataResource
import com.android.habibi.core.ui.utils.DataMapper.mapMovieDomainToPresentation

class MovieViewModel constructor(
    private val movieUseCase: IMovieUseCase
) : ViewModel() {
    private val loadTrigger = MutableLiveData(Unit)

    fun refreshData(){
        loadTrigger.value = Unit
    }

    fun getMovie(): LiveData<DataResource<List<MoviePresentation>>> = loadTrigger.switchMap {
        Transformations.map(movieUseCase.getAllMovie().asLiveData()){
            when(it){
                is Resource.Success -> {
                    if (!it.data.isNullOrEmpty())
                        DataResource.Success(
                            mapMovieDomainToPresentation(it.data!!)
                        )
                    else
                        DataResource.Empty()
                }
                is Resource.Loading -> {
                    DataResource.Loading()
                }
                is Resource.Error -> {
                    DataResource.Error(it.message!!)
                }
            }
        }
    }
}