package com.android.habibi.core.di

import androidx.room.Room
import com.android.habibi.core.BuildConfig
import com.android.habibi.core.data.MovieCatalogueRepository
import com.android.habibi.core.data.source.local.LocalDataSource
import com.android.habibi.core.data.source.local.room.MovieCatalogueDatabase
import com.android.habibi.core.data.source.remote.RemoteDataSource
import com.android.habibi.core.data.source.remote.network.ApiService
import com.android.habibi.core.domain.repository.IMovieRepository
import com.android.habibi.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<MovieCatalogueDatabase>().movieCatalogueDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            MovieCatalogueDatabase::class.java, "MovieCatalogue.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()

        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IMovieRepository> { MovieCatalogueRepository(get(), get(), get()) }
}