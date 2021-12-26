package com.android.habibi.core.data

import com.android.habibi.core.data.source.local.LocalDataSource
import com.android.habibi.core.data.source.remote.RemoteDataSource
import com.android.habibi.core.data.source.remote.network.ApiResponse
import com.android.habibi.core.data.source.remote.response.ResultsItem
import com.android.habibi.core.domain.model.Movie
import com.android.habibi.core.domain.repository.IMovieRepository
import com.android.habibi.core.utils.AppExecutors
import com.android.habibi.core.utils.DataMapper.mapDomainToEntity
import com.android.habibi.core.utils.DataMapper.mapEntitiesToDomain
import com.android.habibi.core.utils.DataMapper.mapResponseToEntities
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieCatalogueRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): IMovieRepository {

    override fun getAllMovie(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<ResultsItem>>() {
            override fun loadFromDB(): Flow<List<Movie>> =
                localDataSource.getMovie().map {
                    mapEntitiesToDomain(it)
                }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                true

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsItem>>> =
                remoteDataSource.getMovieNowPlaying()

            override suspend fun saveCallResult(data: List<ResultsItem>) {
                val movieList = mapResponseToEntities(data)
                localDataSource.insertMovie(movieList)
            }

        }.asFlow()

    override fun getFavoriteMovie(): Flow<List<Movie>> =
        localDataSource.getFavorite().map {
            mapEntitiesToDomain(it)
        }

    override fun setFavoriteMovie(movie: Movie, state: Boolean) {
        val movieEntity = mapDomainToEntity(movie)
        appExecutors.diskIO().execute {
            localDataSource.setFavorite(movieEntity, state)
        }
    }
}