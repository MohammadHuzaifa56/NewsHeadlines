package com.example.newsheadlines.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsheadlines.core.Constants
import com.example.newsheadlines.data.paging.NewsPagingSource
import com.example.newsheadlines.data.remote.NewsRemoteDataSource
import com.example.newsheadlines.domain.model.News
import com.example.newsheadlines.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsRemoteDataSource: NewsRemoteDataSource
) : NewsRepository {
    override suspend fun getTopHeadlines(): Flow<PagingData<News>> {
        return Pager(
            config = PagingConfig(pageSize = Constants.MAX_PAGE_SIZE, prefetchDistance = 2),
            pagingSourceFactory = {
                NewsPagingSource(newsRemoteDataSource)
            }
        ).flow
    }
}