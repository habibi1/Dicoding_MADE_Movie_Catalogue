package com.android.habibi.favorite

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.android.habibi.core.domain.usecase.IMovieUseCase
import com.android.habibi.core.ui.DataResource
import com.android.habibi.core.ui.utils.DataMapper
import com.android.habibi.preference.SettingPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import com.android.habibi.core.domain.model.Movie as MovieDomain
import com.android.habibi.core.ui.model.Movie as MoviePresentation

@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest{

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
        "/tuaKitJJIaqZPyMz7rhb4Yxm.jpg"
    )

    private val list = ArrayList<MovieDomain>()

    private lateinit var viewModel: FavoriteViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var iMovieUseCase: IMovieUseCase

    private lateinit var settingPreferences: SettingPreferences

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var observerFromDatabase: Observer<DataResource<List<MoviePresentation>>>

    @ExperimentalCoroutinesApi
    @Before
    fun before(){
        list.addAll(listOf(
            dataDummy1, dataDummy2
        ))

        Dispatchers.setMain(Dispatchers.Unconfined)
        settingPreferences = SettingPreferences(context)
    }

    @Test
    fun getMovieSuccessNotEmpty() {
        Mockito.`when`(iMovieUseCase.getAllFavoriteMovie()).thenReturn(
            flowOf(list)
        )

        viewModel = FavoriteViewModel(iMovieUseCase, settingPreferences)

        val callMethod = viewModel.listFavoriteMovie
        callMethod.observeForever(observerFromDatabase)
        val result = callMethod.value
        assertNotNull(result)
        assertTrue(result is DataResource.Success)
        assertEquals(result?.message, null)
        assertEquals(result?.data, DataMapper.mapMovieDomainToPresentation(list))
    }

    @Test
    fun getMovieSuccessEmpty() {
        Mockito.`when`(iMovieUseCase.getAllFavoriteMovie()).thenReturn(
            flowOf(ArrayList())
        )

        viewModel = FavoriteViewModel(iMovieUseCase, settingPreferences)

        val callMethod = viewModel.listFavoriteMovie
        callMethod.observeForever(observerFromDatabase)
        val result = callMethod.value
        assertNotNull(result)
        assertTrue(result is DataResource.Empty)
        assertEquals(result?.message, null)
        assertEquals(result?.data, null)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}