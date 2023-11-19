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
import retrofit2.converter.gson.GsonConverterFactory

class NewsAPIsTest {

    lateinit var mockWebServer: MockWebServer
    lateinit var newsAPI: NewsAPI

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        newsAPI = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(NewsAPI::class.java)
    }

    @Test
    fun getNewsHeadline_should_return_empty_list() = runTest{
        val mockResponse = MockResponse()
        val testResponse = NewsResponse(status = "200", totalResults = 0, articles = emptyList())
        mockResponse.setBody(Gson().toJson(testResponse))
        mockWebServer.enqueue(mockResponse)

        val response = newsAPI.getTopHeadlines(key = "", page = 1, pageSize = 10)
        mockWebServer.takeRequest()
        Assert.assertEquals(true, response.articles?.isEmpty())
        Assert.assertEquals(true, response.totalResults == 0)
    }

    @Test
    fun getNewsHeadlines_should_return_right_items() = runTest{
        val mockResponse = MockResponse()
        val testResponse = NewsResponse(status = "200", totalResults = 0, articles = listOf(
            NewsDto(title = "title 1", description = "description 1"),
            NewsDto(title = "title 2", description = "description 2"),
        ))
        mockResponse.setBody(Gson().toJson(testResponse))
        mockWebServer.enqueue(mockResponse)

        val response = newsAPI.getTopHeadlines(key = "", page = 1, pageSize = 10)
        mockWebServer.takeRequest()
        Assert.assertEquals(true, response.articles?.size == 2)
        Assert.assertEquals(true, response.articles?.get(0)?.title == "title 1")
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}