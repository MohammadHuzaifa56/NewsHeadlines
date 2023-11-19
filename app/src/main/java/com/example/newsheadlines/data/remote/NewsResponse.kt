package com.example.newsheadlines.data.remote

import com.example.newsheadlines.data.model.NewsDto
import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("status")
    var status: String? = null,

    @SerializedName("totalResults")
    var totalResults: Int? = null,

    @SerializedName("articles")
    var articles: List<NewsDto>?= null
)
