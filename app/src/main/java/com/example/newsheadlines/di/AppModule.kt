package com.example.newsheadlines.di

import com.example.newsheadlines.data.remote.NewsAPI
import com.example.newsheadlines.data.remote.NewsRemoteDataSource
import com.example.newsheadlines.data.repository.NewsRepositoryImpl
import com.example.newsheadlines.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesNewsRemoteDataSource(
        api: NewsAPI
    ): NewsRemoteDataSource{
        return NewsRemoteDataSource(api)
    }


    @Provides
    @Singleton
    fun providesNewsRepository(
        newsRemoteDataSource: NewsRemoteDataSource
    ): NewsRepository {
        return NewsRepositoryImpl(newsRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor { chain ->
            val originalRequest = chain.request()
            val modifiedRequest = originalRequest.newBuilder()
                .build()
            chain.proceed(modifiedRequest)
        }
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun providesNewsAPI(client: OkHttpClient): NewsAPI {
        return Retrofit.Builder()
            .baseUrl(NewsAPI.BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

}