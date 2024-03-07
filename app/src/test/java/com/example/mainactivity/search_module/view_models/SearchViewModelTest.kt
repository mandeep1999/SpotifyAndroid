package com.example.mainactivity.search_module.view_models


import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.util.Log
import com.example.mainactivity.core.utils.Resource
import com.example.mainactivity.getOrAwaitValue
import com.example.mainactivity.search_module.data.models.response.SearchResponse
import com.example.mainactivity.search_module.repositories.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SearchViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    lateinit var searchRepository: SearchRepository

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getSearchResponseLiveData() = runTest {
        Mockito.`when`(searchRepository.getSearchResults("")).thenReturn(Resource.Success(
            SearchResponse()
        ))
        val sut = SearchViewModel(searchRepository)
        sut.getSearchResults("")
        val result = sut.searchResponseLiveData.getOrAwaitValue()
        Assert.assertEquals(true, result.data == null)
    }

    @Test
    fun getRecentSearchesLiveData() {
    }

    @Test
    fun getSearchResults() {
    }

    @Test
    fun getRecentSearchesFromDB() {
    }

    @Test
    fun insertRecentSearchIntoDB() {
    }
}