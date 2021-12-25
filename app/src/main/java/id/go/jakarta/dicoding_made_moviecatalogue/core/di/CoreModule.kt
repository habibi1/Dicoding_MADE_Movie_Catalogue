package id.go.jakarta.dicoding_made_moviecatalogue.core.di

import androidx.room.Room
import id.go.jakarta.dicoding_made_moviecatalogue.BuildConfig
import id.go.jakarta.dicoding_made_moviecatalogue.core.data.MovieCatalogueRepository
import id.go.jakarta.dicoding_made_moviecatalogue.core.data.source.local.LocalDataSource
import id.go.jakarta.dicoding_made_moviecatalogue.core.data.source.local.room.MovieCatalogueDatabase
import id.go.jakarta.dicoding_made_moviecatalogue.core.data.source.remote.RemoteDataSource
import id.go.jakarta.dicoding_made_moviecatalogue.core.data.source.remote.network.ApiService
import id.go.jakarta.dicoding_made_moviecatalogue.core.domain.repository.IMovieRepository
import id.go.jakarta.dicoding_made_moviecatalogue.core.utils.AppExecutors
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
    single<IMovieRepository> { MovieCatalogueRepository(get(), get(), get())}
}