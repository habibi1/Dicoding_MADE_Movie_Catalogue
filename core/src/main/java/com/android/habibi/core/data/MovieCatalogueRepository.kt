package com.android.habibi.core.data

import com.android.habibi.core.data.source.local.LocalDataSource
import com.android.habibi.core.data.source.local.entity.MovieEntity
import com.android.habibi.core.data.source.remote.RemoteDataSource
import com.android.habibi.core.data.source.remote.network.ApiResponse
import com.android.habibi.core.data.source.remote.response.MovieDetailResponse
import com.android.habibi.core.data.source.remote.response.ResultsItem
import com.android.habibi.core.domain.model.Movie
import com.android.habibi.core.domain.model.MovieDetail as MovieDetailDomain
//import com.android.habibi.core.ui.model.MovieDetail as MovieDetailPresentation
import com.android.habibi.core.domain.repository.IMovieRepository
import com.android.habibi.core.utils.AppExecutors
import com.android.habibi.core.data.utils.DataMapper.mapListMovieResponseToDomain
import com.android.habibi.core.data.utils.DataMapper.mapMovieDetailResponseToDomain
import com.android.habibi.core.data.utils.DataMapper.mapMovieDomainToEntities
import com.android.habibi.core.data.utils.DataMapper.mapMovieEntitiesToDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieCatalogueRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): IMovieRepository {

    override fun getAllMovie(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<ResultsItem>>() {
            override fun loadFromNetwork(data: List<ResultsItem>): Flow<List<Movie>> =
                mapListMovieResponseToDomain(data)
            override suspend fun createCall(): Flow<ApiResponse<List<ResultsItem>>> =
                remoteDataSource.getMovieNowPlaying()

        }.asFlow()

    override fun getDetailMovie(movieId: String): Flow<Resource<MovieDetailDomain>> =
        object : NetworkBoundResource<MovieDetailDomain, MovieDetailResponse>() {
            override fun loadFromNetwork(data: MovieDetailResponse): Flow<MovieDetailDomain> =
                mapMovieDetailResponseToDomain(data)
            override suspend fun createCall(): Flow<ApiResponse<MovieDetailResponse>> =
                remoteDataSource.getDetailMovie(movieId)
        }.asFlow()

    override fun getAllFavoriteMovie(): Flow<List<Movie>> =
        localDataSource.getFavorite().map {
            mapMovieEntitiesToDomain(it)
        }

    override suspend fun deleteFavoriteMovie(movie: MovieDetailDomain): Int {
        val movieEntity = mapMovieDomainToEntities(movie)
        return localDataSource.deleteFavoriteMovie(movieEntity)
    }

    override suspend fun insertFavoriteMovie(movie: MovieDetailDomain) {
        val movieEntity = mapMovieDomainToEntities(movie)
        return localDataSource.insertMovie(movieEntity)
    }

    override fun isFavoriteMovie(id: String): Flow<MovieEntity> =
            localDataSource.isFavoriteMovie(id)
}