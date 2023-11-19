package com.example.newsheadlines.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsheadlines.core.Constants
import com.example.newsheadlines.data.mapper.toNewsList
import com.example.newsheadlines.data.remote.NewsRemoteDataSource
import com.example.newsheadlines.domain.model.News
import retrofit2.HttpException
import java.io.IOException

class NewsPagingSource(
    private val newsRemoteDataSource: NewsRemoteDataSource
) : PagingSource<Int, News>() {
    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        return try {
            val currentPage = params.key ?: 1
            val news = newsRemoteDataSource.getTopHeadlines(
                pageNumber = currentPage,
                pageSize = Constants.MAX_PAGE_SIZE,
                apiKey = Constants.API_KEY
            )
            LoadResult.Page(
                data = news.articles?.toNewsList().orEmpty(),
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (news.articles?.isEmpty() == true) null else currentPage.plus(1)
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}