@file:Suppress("unused")

package com.android.habibi.dicoding_made_moviecatalogue

import android.app.Application
import com.android.habibi.core.di.databaseModule
import com.android.habibi.core.di.networkModule
import com.android.habibi.core.di.repositoryModule
import com.android.habibi.dicoding_made_moviecatalogue.di.useCaseModule
import com.android.habibi.dicoding_made_moviecatalogue.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}