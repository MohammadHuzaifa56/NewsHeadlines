package com.example.newsheadlines.domain.repository

import androidx.paging.PagingData
import com.example.newsheadlines.domain.model.News
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getTopHeadlines(): Flow<PagingData<News>>
}