package com.android.habibi.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.android.habibi.core.domain.usecase.IMovieUseCase
import com.android.habibi.preference.SettingPreferences
import kotlinx.coroutines.launch

class FavoriteViewModel constructor(
    movieUseCase: IMovieUseCase,
    private val pref: SettingPreferences
) : ViewModel() {
    val listFavoriteMovie = movieUseCase.getAllFavoriteMovie().asLiveData()

    fun getTypeListSetting(): LiveData<Int> {
        return pref.getTypeListSetting().asLiveData()
    }

    fun saveTypeListSetting(typeList: Int) {
        viewModelScope.launch {
            pref.saveTypeListSetting(typeList)
        }
    }
}