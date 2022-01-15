package com.android.habibi.dicoding_made_moviecatalogue.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.android.habibi.core.data.Resource
import com.android.habibi.core.domain.usecase.IMovieUseCase
import com.android.habibi.core.ui.DataResource
import com.android.habibi.core.ui.utils.DataMapper.mapMovieDomainToPresentation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import com.android.habibi.core.domain.model.Movie as MovieDomain
import com.android.habibi.core.ui.model.Movie as MoviePresentation
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    private val dataDummy1 = MovieDomain(
        634649,
        "Spider-Man: No Way Home",
        8.4,
        "/1Rr5SrvHxMXHu5RjKpaMba8VTzi.jpg"
    )

    private val dataDummy2 = MovieDomain(
        438695,
        "Sing 2",
        8.0,
        "/tuaKitJJIaqZPyMz7hb4Yxm.jpg"
    )

    private val list = ArrayList<MovieDomain>()

    private lateinit var viewModel: MovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var iMovieUseCase: IMovieUseCase

    @Mock
    private lateinit var observerFromNetwork: Observer<DataResource<List<MoviePresentation>>>

    @ExperimentalCoroutinesApi
    @Before
    fun before(){
        list.addAll(listOf(
            dataDummy1, dataDummy2
        ))

        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = MovieViewModel(iMovieUseCase)
    }

    @Test
    fun getMovieSuccessNotEmpty() {
        Mockito.`when`(iMovieUseCase.getAllMovie()).thenReturn(
            flowOf(Resource.Success(list))
        )

        val callMethod = viewModel.getMovie()
        callMethod.observeForever(observerFromNetwork)
        val result = callMethod.value
        assertNotNull(result)
        assertTrue(result is DataResource.Success)
        assertEquals(result?.message, null)
        assertEquals(result?.data, mapMovieDomainToPresentation(list))
    }

    @Test
    fun getMovieLoading() {
        Mockito.`when`(iMovieUseCase.getAllMovie()).thenReturn(
            flowOf(Resource.Loading())
        )

        val callMethod = viewModel.getMovie()
        callMethod.observeForever(observerFromNetwork)
        val result = callMethod.value
        assertNotNull(result)
        assertTrue(result is DataResource.Loading)
        assertEquals(result?.message, null)
        assertEquals(result?.data, null)
    }

    @Test
    fun getMovieError() {
        val message = "message"

        Mockito.`when`(iMovieUseCase.getAllMovie()).thenReturn(
            flowOf(Resource.Error(message))
        )

        val callMethod = viewModel.getMovie()
        callMethod.observeForever(observerFromNetwork)
        val result = callMethod.value
        assertNotNull(result)
        assertTrue(result is DataResource.Error)
        assertEquals(result?.message, message)
        assertEquals(result?.data, null)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}