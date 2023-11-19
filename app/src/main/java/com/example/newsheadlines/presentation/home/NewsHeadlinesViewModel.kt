package com.example.newsheadlines.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsheadlines.data.repository.NewsRepositoryImpl
import com.example.newsheadlines.domain.model.News
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsHeadlinesViewModel @Inject constructor(
    private val newsRepositoryImpl: NewsRepositoryImpl
) : ViewModel() {
    private val _newsState: MutableStateFlow<PagingData<News>> =
        MutableStateFlow(value = PagingData.empty())
    val newsState: MutableStateFlow<PagingData<News>> get() = _newsState

    init {
        getNewsHeadlines()
    }

    fun getNewsHeadlines() {
        viewModelScope.launch {
            newsRepositoryImpl.getTopHeadlines()
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _newsState.value = it
                }
        }
    }
}