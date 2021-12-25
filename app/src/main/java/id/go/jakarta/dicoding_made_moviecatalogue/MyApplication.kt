package id.go.jakarta.dicoding_made_moviecatalogue

import android.app.Application
import id.go.jakarta.dicoding_made_moviecatalogue.core.di.databaseModule
import id.go.jakarta.dicoding_made_moviecatalogue.core.di.networkModule
import id.go.jakarta.dicoding_made_moviecatalogue.core.di.repositoryModule
import id.go.jakarta.dicoding_made_moviecatalogue.di.useCaseModule
import id.go.jakarta.dicoding_made_moviecatalogue.di.viewModelModule
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