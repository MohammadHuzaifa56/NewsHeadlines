package com.example.newsheadlines.data.mapper

import com.example.newsheadlines.data.model.NewsDto
import com.example.newsheadlines.domain.model.News

fun NewsDto.toEntity() = News(
    author = this.author.orEmpty(),
    title = this.title.orEmpty(),
    description = this.description.orEmpty(),
    url = this.url.orEmpty(),
    urlToImage = this.urlToImage.orEmpty(),
    publishedAt = this.publishedAt.orEmpty(),
    content = this.content.orEmpty()
)

fun News.toDto() = NewsDto(
    author = this.author,
    title = this.title,
    description = this.description,
    url = this.url,
    urlToImage = this.urlToImage,
    publishedAt = this.publishedAt,
    content = this.content
)

fun List<NewsDto>.toNewsList(): List<News> = this.map {
    it.toEntity()
}

fun List<News>.toNewsListDto(): List<NewsDto> = this.map {
    it.toDto()
}