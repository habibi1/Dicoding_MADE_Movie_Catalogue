package com.android.habibi.di

import com.android.habibi.favorite.FavoriteViewModel
import com.android.habibi.preference.SettingPreferences
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingPreferencesModule = module {
    single {
        SettingPreferences(
            get()
        )
    }
}

val favoriteModule = module {
    viewModel { FavoriteViewModel(get(), get()) }
}