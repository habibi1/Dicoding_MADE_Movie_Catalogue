package com.android.habibi.core.data.source.remote

import com.android.habibi.core.data.source.remote.network.ApiResponse
import com.android.habibi.core.data.source.remote.network.ApiService
import com.android.habibi.core.data.source.remote.response.GenresItem
import com.android.habibi.core.data.source.remote.response.MovieDetailResponse
import com.android.habibi.core.data.source.remote.response.MovieResponse
import com.android.habibi.core.data.source.remote.response.ResultsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RemoteDataSourceTest {

    private val dataDummy1 = ResultsItem(
        "Spider-Man: No Way Home",
        "/1Rr5SrvHxMXHu5RjKpaMba8VTzi.jpg",
        8.4,
        634649
    )

    private val dataDummy2 = ResultsItem(
        "Sing 2",
        "/tuaKitJJIaqZPyMz7rxb4Yxm.jpg",
        8.0,
        438695
    )

    private val list = ArrayList<ResultsItem>()

    private val genresItem = GenresItem("Action", 1)

    private val movieDetailResponse = MovieDetailResponse(
        "Sing 2",
        "/taKitJJIaqZPyMz7rhb4Yxm.jpg",
        listOf(genresItem),
        438695,
        559,
        "Buster and his new cast now have their sights set on debuting a new show at the Crystal Tower Theater in glamorous Red City. But with no connections, he and his singers must sneak into the Crystal Entertainment offices, run by the ruthless wolf mogul Jimmy Crystal, where the gang pitches the ridiculous idea of casting the lion rock legend Clay Calloway in their show. Buster must embark on a quest to find the now-isolated Clay and persuade him to return to the stage.",
        110,
        "/aWeTRFwY8txG5uCj4rMCfSP.jpg",
        8.0,
        false
    )

    private val movieId = movieDetailResponse.id.toString()

    private lateinit var movieResponse: MovieResponse

    private lateinit var remoteDataSource: RemoteDataSource

    @Mock
    private lateinit var apiService: ApiService

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        remoteDataSource = RemoteDataSource(apiService)

        list.addAll(listOf(dataDummy1, dataDummy2))

        movieResponse = MovieResponse(list)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getMovieNowPlayingSuccess() = runBlocking {
        Mockito.`when`(apiService.getMovieNowPlaying()).thenReturn(movieResponse)

        val call = remoteDataSource.getMovieNowPlaying().first()
        assertNotNull(call)
        assertTrue(call is ApiResponse.Success)
        assertEquals(call, ApiResponse.Success(list))
    }

    @Test
    fun getMovieNowPlayingError() = runBlocking {
        Mockito.`when`(apiService.getMovieNowPlaying()).thenReturn(null)

        val call = remoteDataSource.getMovieNowPlaying().first()
        assertNotNull(call)
        assertTrue(call is ApiResponse.Error)
    }

    @Test
    fun getDetailMovieSuccess() = runBlocking {
        Mockito.`when`(apiService.getDetailMovie(movieId)).thenReturn(movieDetailResponse)

        val call = remoteDataSource.getDetailMovie(movieId).first()
        assertNotNull(call)
        assertTrue(call is ApiResponse.Success)
        assertEquals(call, ApiResponse.Success(movieDetailResponse))
    }

    @Test
    fun getDetailMovieError() = runBlocking {
        Mockito.`when`(apiService.getDetailMovie(movieId)).thenReturn(null)

        val call = remoteDataSource.getDetailMovie(movieId).first()
        assertNotNull(call)
        assertTrue(call is ApiResponse.Success)
    }
}