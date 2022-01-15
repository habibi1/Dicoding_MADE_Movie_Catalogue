package com.android.habibi.dicoding_made_moviecatalogue.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.android.habibi.core.data.Resource
import com.android.habibi.core.data.source.local.entity.MovieEntity
import com.android.habibi.core.data.utils.DataMapper.mapMovieDomainToEntities
import com.android.habibi.core.domain.model.MovieDetail as MovieDetailDomain
import com.android.habibi.core.domain.usecase.IMovieUseCase
import com.android.habibi.core.ui.DataResource
import com.android.habibi.core.ui.utils.DataMapper.mapMovieDetailToPresentation
import com.android.habibi.core.ui.utils.DataMapper.mapMoviePresentationToDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailMovieViewModelTest {

    private val dataDummy = MovieDetailDomain(
        "Spider-Man: No Way Home",
        "/1Rr5SrvHxMXHu5RjKpaMba8VTzi.jpg",
        634649,
        4078,
        "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.",
        2,
        "/path.jpg",
        8.4,
        false,
        listOf("Action", "Adventure", "Science Fiction")
    )

    private val dataDummyFromEntity = mapMovieDomainToEntities(dataDummy)

    private val dataDummyFromPresentation = mapMovieDetailToPresentation(dataDummy)

    private val movieId = dataDummy.id.toString()

    private lateinit var viewModel: DetailMovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var iMovieUseCase: IMovieUseCase

    @Mock
    private lateinit var observerFromNetwork: Observer<DataResource<com.android.habibi.core.ui.model.MovieDetail>>

    @Mock
    private lateinit var observerFromDatabase: Observer<MovieEntity>

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = DetailMovieViewModel(iMovieUseCase)
    }

    @Test
    fun getDataSuccess() {

        val movieId = dataDummy.id.toString()

        `when`(iMovieUseCase.getDetailMovie(movieId)).thenReturn(flowOf(Resource.Success(
            dataDummy
        )))

        val callMethod = viewModel.getData(movieId)
        callMethod.observeForever(observerFromNetwork)
        assertNotNull(callMethod.value)
        assertTrue(callMethod.value is DataResource.Success)
        assertEquals(callMethod.value?.message, null)
        assertEquals(callMethod.value?.data, mapMovieDetailToPresentation(dataDummy))
    }

    @Test
    fun getDataLoading()  {

        val movieId = dataDummy.id.toString()

        `when`(iMovieUseCase.getDetailMovie(movieId)).thenReturn(flowOf(Resource.Loading()))

        val callMethod = viewModel.getData(movieId)
        callMethod.observeForever(observerFromNetwork)
        assertNotNull(callMethod.value)
        assertTrue(callMethod.value is DataResource.Loading)
        assertEquals(callMethod.value?.data, null)
        assertEquals(callMethod.value?.message, null)
    }

    @Test
    fun getDataError()  {
        val message = "Message"

        `when`(iMovieUseCase.getDetailMovie(movieId)).thenReturn(flowOf(Resource.Error(message)))

        val callMethod = viewModel.getData(movieId)
        callMethod.observeForever(observerFromNetwork)
        assertNotNull(callMethod.value)
        assertTrue(callMethod.value is DataResource.Error)
        assertEquals(callMethod.value?.message, message)
        assertEquals(callMethod.value?.data, null)
    }

    @Test
    fun isFavoriteMovieTrue() {
        `when`(iMovieUseCase.isFavoriteMovie(movieId)).thenReturn(flowOf(dataDummyFromEntity))

        val callMethod = viewModel.isFavoriteMovie(movieId)
        callMethod.observeForever(observerFromDatabase)
        val result = callMethod.value
        assertNotNull(result)
        assertTrue(result != null)
    }

    @Test
    fun isFavoriteMovieFalse() {
        `when`(iMovieUseCase.isFavoriteMovie(movieId)).thenReturn(emptyFlow())

        val callMethod = viewModel.isFavoriteMovie(movieId)
        callMethod.observeForever(observerFromDatabase)
        assertNull(callMethod.value)
    }

    @Test
    fun setFavoriteMovie() {
        val dataMovieFromDomain = mapMoviePresentationToDomain(dataDummyFromPresentation)
        viewModel.setFavoriteMovie(dataDummyFromPresentation)

        assertEquals(dataDummyFromPresentation.title, dataMovieFromDomain.title)
        assertEquals(dataDummyFromPresentation.backdropPath, dataMovieFromDomain.backdropPath)
        assertEquals(dataDummyFromPresentation.id, dataMovieFromDomain.id)
        assertEquals(dataDummyFromPresentation.voteCount, dataMovieFromDomain.voteCount)
        assertEquals(dataDummyFromPresentation.overview, dataMovieFromDomain.overview)
        assertEquals(dataDummyFromPresentation.hourRuntime*60 + dataDummyFromPresentation.minuteRuntime, dataMovieFromDomain.runtime)
        assertEquals(dataDummyFromPresentation.posterPath, dataMovieFromDomain.posterPath)
        assertTrue(dataDummyFromPresentation.voteAverage.toDouble() == dataMovieFromDomain.voteAverage)
        assertEquals(dataDummyFromPresentation.adult, dataMovieFromDomain.adult)
        assertEquals(dataDummyFromPresentation.genres, dataMovieFromDomain.genres)
    }

    @Test
    fun deleteFavoriteMovie() {
        val dataMovieFromDomain = mapMoviePresentationToDomain(dataDummyFromPresentation)
        viewModel.deleteFavoriteMovie(dataDummyFromPresentation)

        assertEquals(dataDummyFromPresentation.title, dataMovieFromDomain.title)
        assertEquals(dataDummyFromPresentation.backdropPath, dataMovieFromDomain.backdropPath)
        assertEquals(dataDummyFromPresentation.id, dataMovieFromDomain.id)
        assertEquals(dataDummyFromPresentation.voteCount, dataMovieFromDomain.voteCount)
        assertEquals(dataDummyFromPresentation.overview, dataMovieFromDomain.overview)
        assertEquals(dataDummyFromPresentation.hourRuntime*60 + dataDummyFromPresentation.minuteRuntime, dataMovieFromDomain.runtime)
        assertEquals(dataDummyFromPresentation.posterPath, dataMovieFromDomain.posterPath)
        assertTrue(dataDummyFromPresentation.voteAverage.toDouble() == dataMovieFromDomain.voteAverage)
        assertEquals(dataDummyFromPresentation.adult, dataMovieFromDomain.adult)
        assertEquals(dataDummyFromPresentation.genres, dataMovieFromDomain.genres)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}