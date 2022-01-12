package com.android.habibi.favorite

import androidx.lifecycle.*
import com.android.habibi.core.domain.usecase.IMovieUseCase
import com.android.habibi.core.ui.DataResource
import com.android.habibi.core.ui.model.Movie
import com.android.habibi.core.ui.utils.DataMapper
import com.android.habibi.preference.SettingPreferences
import kotlinx.coroutines.launch

class FavoriteViewModel constructor(
    movieUseCase: IMovieUseCase,
    private val pref: SettingPreferences
) : ViewModel() {
    val listFavoriteMovie : LiveData<DataResource<List<Movie>>> =
        Transformations.map(
            movieUseCase.getAllFavoriteMovie().asLiveData()
        ) {
            if (it.isNullOrEmpty())
                DataResource.Empty()
            else
                DataResource.Success(
                    DataMapper.mapMovieDomainToPresentation(it)
                )
        }

    fun getTypeListSetting(): LiveData<Int> {
        return pref.getTypeListSetting().asLiveData()
    }

    fun saveTypeListSetting(typeList: Int) {
        viewModelScope.launch {
            pref.saveTypeListSetting(typeList)
        }
    }
}