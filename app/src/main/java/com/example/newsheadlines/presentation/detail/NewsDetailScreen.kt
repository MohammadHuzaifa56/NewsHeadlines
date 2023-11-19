package com.example.newsheadlines.presentation.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.newsheadlines.R
import com.example.newsheadlines.domain.model.News

@Composable
fun NewsDetailScreen(news: News) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .scrollable(rememberScrollState(), Orientation.Vertical)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = news.urlToImage),
            contentDescription = stringResource(
                R.string.detail_image
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = news.title, fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 10.dp),
            color = Color.Black,
            style = TextStyle(fontWeight = FontWeight.SemiBold)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = news.content, fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 10.dp),
            color = Color.DarkGray,
            style = TextStyle(fontWeight = FontWeight.Normal)
        )
    }
}