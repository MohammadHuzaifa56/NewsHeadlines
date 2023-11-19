package com.example.newsheadlines

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import androidx.paging.map
import com.example.newsheadlines.data.repository.NewsRepositoryImpl
import com.example.newsheadlines.domain.model.News
import com.example.newsheadlines.presentation.home.NewsHeadlinesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class NewsHeadlinesViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var newsRepository: NewsRepositoryImpl

    private lateinit var viewModel: NewsHeadlinesViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testCoroutineDispatcher)
        viewModel = NewsHeadlinesViewModel(newsRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getNewsHeadlines should update newsState`() = runBlockingTest{

        val emptyNewsFlow: Flow<PagingData<News>> = flow {
            emit(PagingData.from(emptyList()))
        }
        `when`(newsRepository.getTopHeadlines()).thenReturn(emptyNewsFlow)

        viewModel.getNewsHeadlines()

        val result = viewModel.newsState.value
        assertEquals(false, listOf(result.map { it }).isEmpty() )
    }
}