package com.example.newsheadlines.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.example.newsheadlines.BuildConfig
import com.example.newsheadlines.domain.model.News
import com.example.newsheadlines.core.ScreenRoutes

@Composable
fun NewsHeadlinesListScreen(
    viewModel: NewsHeadlinesViewModel = hiltViewModel(),
    navController: NavController
){
    val newsPagingItems: LazyPagingItems<News> = viewModel.newsState.collectAsLazyPagingItems()
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = BuildConfig.SOURCE_TITLE,
                fontSize = 14.sp,
                color = Color.Black,
                style = TextStyle(fontWeight = FontWeight.Bold)
            )
        }
        Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = Color.LightGray)

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .wrapContentHeight()
        ) {
            if (newsPagingItems.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    items(newsPagingItems.itemCount) { index ->
                        newsPagingItems[index]?.let {
                            NewsItem(news = it, navController)
                        }
                    }
                    item {
                        if (newsPagingItems.loadState.append is LoadState.Loading) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NewsItem(news: News, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(start = 10.dp)
            .clickable {
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    key = "news",
                    value = news
                )
                navController.navigate(ScreenRoutes.DETAILS_SCREEN)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = news.urlToImage),
            contentDescription = "Image",
            modifier = Modifier
                .width(100.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = news.title,
                fontSize = 12.sp,
                color = Color.Black,
                style = TextStyle(fontWeight = FontWeight.SemiBold)
            )
        }
    }
}