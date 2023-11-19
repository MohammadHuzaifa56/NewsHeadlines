package com.example.newsheadlines.data.remote

import com.example.newsheadlines.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("sources") source: String = BuildConfig.NEWS_SOURCE,
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int,
        @Query("apiKey") key: String
    ): NewsResponse

    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
    }
}