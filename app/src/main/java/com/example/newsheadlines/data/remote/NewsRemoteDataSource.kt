package com.example.newsheadlines.data.remote

import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor(
    private val api: NewsAPI
) {
    suspend fun getTopHeadlines(
        apiKey: String,
        pageSize: Int,
        pageNumber: Int
    ): NewsResponse = api.getTopHeadlines(
        pageSize = pageSize,
        page = pageNumber,
        key = apiKey
    )
}