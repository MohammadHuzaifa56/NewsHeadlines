package com.example.newsheadlines

import com.example.newsheadlines.data.model.NewsDto
import com.example.newsheadlines.data.remote.NewsAPI
import com.example.newsheadlines.data.remote.NewsResponse
import com.google.gson.Gson
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class NewsPagingSourceTest {
    private lateinit var mockWebServer: MockWebServer
    lateinit var newsAPI: NewsAPI

    companion object {
        val newsResponse = NewsResponse(
            status = null,
            totalResults = 1,
            articles = listOf(
                NewsDto(
                    title = "news title 1",
                    description = "news description 1",
                    content = "news content 1"
                )
            )
        )
    }

    @Before
    fun setup(){
        mockWebServer = MockWebServer()
        newsAPI = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(NewsAPI::class.java)
    }

    @Test
    fun `news paging source load - success`(): Unit = runTest {
        val mockResponse = MockResponse()
        mockResponse.setBody(Gson().toJson(newsResponse))
        mockWebServer.enqueue(mockResponse)
        val result = newsAPI.getTopHeadlines(pageSize = 10, page = 1, key = "")
        mockWebServer.takeRequest()
        Assert.assertEquals(1, result.articles?.size)
    }

    @Test
    fun `news paging source load - error`(): Unit = runTest {
        val mockResponse = MockResponse()
        mockResponse.setBody(
            Gson().toJson(
                NewsResponse(
                    status = "403"
                )
            )
        )
        mockWebServer.enqueue(mockResponse)

        val result = newsAPI.getTopHeadlines(pageSize = 10, page = 1, key = "")
        mockWebServer.takeRequest()

        Assert.assertEquals("403", result.status)
    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }
}