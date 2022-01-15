package com.android.habibi.core.domain.usecase

import com.android.habibi.core.data.Resource
import com.android.habibi.core.data.utils.DataMapper
import com.android.habibi.core.domain.model.Movie
import com.android.habibi.core.domain.model.MovieDetail
import com.android.habibi.core.domain.repository.IMovieRepository
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieInteractorTest {

    private val dataDummy1 = Movie(
        634649,
        "Spider-Man: No Way Home",
        8.4,
        "/1Rr5SrvHxMXHu5RjKpaMba8VTzi.jpg"
    )

    private val dataDummy2 = Movie(
        438695,
        "Sing 2",
        8.0,
        "/tuaKitJJIaqZPyMz7rxb4Yxm.jpg"
    )

    private val list = ArrayList<Movie>()

    private val dataDummy = MovieDetail(
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

    private val dataDummyFromEntity = DataMapper.mapMovieDomainToEntities(dataDummy)

    private val movieId = dataDummy.id.toString()

    @Mock
    private lateinit var iMovieRepository: IMovieRepository

    private lateinit var movieInteractor: MovieInteractor

    private val massage = "massage"

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        movieInteractor = MovieInteractor(iMovieRepository)

        list.addAll(
            listOf(
                dataDummy1,
                dataDummy2
            )
        )
    }

    @Test
    fun getAllMovieLoading() = runBlocking {
        `when`(iMovieRepository.getAllMovie()).thenReturn(
            flowOf(Resource.Loading())
        )

        val call = movieInteractor.getAllMovie().first()
        assertNotNull(call)
        assertTrue(call is Resource.Loading)
        assertEquals(call.data, null)
        assertEquals(call.message, null)
    }

    @Test
    fun getAllMovieSuccess() = runBlocking {
        `when`(iMovieRepository.getAllMovie()).thenReturn(
            flowOf(Resource.Success(list))
        )

        val call = movieInteractor.getAllMovie().first()
        assertNotNull(call)
        assertTrue(call is Resource.Success)
        assertEquals(call.data, list)
        assertEquals(call.message, null)
    }

    @Test
    fun getAllMovieError() = runBlocking {
        `when`(iMovieRepository.getAllMovie()).thenReturn(
            flowOf(Resource.Error(massage))
        )

        val call = movieInteractor.getAllMovie().first()
        assertNotNull(call)
        assertTrue(call is Resource.Error)
        assertEquals(call.data, null)
        assertEquals(call.message, massage)
    }

    @Test
    fun getDetailMovieSuccess() = runBlocking {
        `when`(iMovieRepository.getDetailMovie(movieId)).thenReturn(
            flowOf(Resource.Success(dataDummy))
        )

        val call = movieInteractor.getDetailMovie(movieId).first()
        assertNotNull(call)
        assertTrue(call is Resource.Success)
        assertEquals(call.data, dataDummy)
        assertEquals(call.message, null)
    }

    @Test
    fun getDetailMovieLoading()= runBlocking {
        `when`(iMovieRepository.getDetailMovie(movieId)).thenReturn(
            flowOf(Resource.Loading())
        )

        val call = movieInteractor.getDetailMovie(movieId).first()
        assertNotNull(call)
        assertTrue(call is Resource.Loading)
        assertEquals(call.data, null)
        assertEquals(call.message, null)
    }

    @Test
    fun getDetailMovieError()= runBlocking {
        `when`(iMovieRepository.getDetailMovie(movieId)).thenReturn(
            flowOf(Resource.Error(massage))
        )

        val call = movieInteractor.getDetailMovie(movieId).first()
        assertNotNull(call)
        assertTrue(call is Resource.Error)
        assertEquals(call.data, null)
        assertEquals(call.message, massage)
    }

    @Test
    fun getAllFavoriteMovieSuccess() = runBlocking {
        `when`(iMovieRepository.getAllFavoriteMovie()).thenReturn(
            flowOf(list)
        )

        val call = movieInteractor.getAllFavoriteMovie().first()
        assertNotNull(call)
        assertTrue(call.isNotEmpty())
        assertEquals(call, list)
    }

    @Test
    fun getAllFavoriteMovieEmpty() = runBlocking {
        `when`(iMovieRepository.getAllFavoriteMovie()).thenReturn(
            flowOf(ArrayList())
        )

        val call = movieInteractor.getAllFavoriteMovie().first()
        assertNotNull(call)
        assertTrue(call.isNullOrEmpty())
    }

    @Test
    fun insertFavoriteMovie() = runBlocking {
        `when`(iMovieRepository.insertFavoriteMovie(dataDummy)).thenReturn(Unit)

        movieInteractor.insertFavoriteMovie(dataDummy)
        verify(iMovieRepository).insertFavoriteMovie(dataDummy)
    }

    @Test
    fun deleteFavoriteMovie() = runBlocking {
        `when`(iMovieRepository.deleteFavoriteMovie(dataDummy)).thenReturn(1)

        val call = movieInteractor.deleteFavoriteMovie(dataDummy)
        verify(iMovieRepository).deleteFavoriteMovie(dataDummy)
        assertEquals(call, 1)
    }

    @Test
    fun isFavoriteMovieTrue() {
        `when`(iMovieRepository.isFavoriteMovie(movieId)).thenReturn(flowOf(dataDummyFromEntity))

        val call = movieInteractor.isFavoriteMovie(movieId)
        assertNotNull(call)
    }

    @Test
    fun isFavoriteMovieFalse() {
        `when`(iMovieRepository.isFavoriteMovie(movieId)).thenReturn(null)

        val call = movieInteractor.isFavoriteMovie(movieId)
        assertEquals(call, null)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}