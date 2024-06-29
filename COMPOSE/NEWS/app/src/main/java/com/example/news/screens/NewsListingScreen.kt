package com.example.news.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoubleArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.news.R
import com.example.news.roomdb.News
import com.example.news.ui.theme.NewsTheme
import com.example.news.viewmodel.NewsViewModel

// below code is for route
enum class NewsSreen{
    NEWS_SCREEN,
    WEBVIEW_SCREEN
}

@Composable
fun NewsScreen(
    newsViewModel: NewsViewModel,
    modifier: Modifier = Modifier
){
    var allNewsList = newsViewModel.allNewsList.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        CatagoryList(
            catagories = newsViewModel.apiCatagoryList,
                filterNews = {category ->
                    newsViewModel.getNewsByCategory(category)}
            )
        NewsList(newsList = allNewsList.value,
            onClickWebView = {}
            )
    }
}

@Composable
fun CatagoryList(catagories: List<String>, filterNews: (String) -> Unit, modifier: Modifier = Modifier) {
    LazyRow {
        items(catagories, key = {it}){ catagory ->
            CatagoryCard(catagory = catagory, filterNews = filterNews)
        }
    }
}

@Composable
fun NewsList(
    newsList: List<News>,
    onClickWebView: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(20.dp)
    ) {
        items(newsList, {news -> news.newsId}){ news ->
            NewsCard(photoUrl = news.imageUrl, onClickWebView = onClickWebView, newsTitle = news.title)
        }
    }
}

@Composable
fun CatagoryCard(catagory: String, filterNews: (String) -> Unit,modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = Modifier
            .padding(4.dp)
            .width(120.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = {
            filterNews(catagory)
        }
    ) {
        Text(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally),
            text = catagory
        )
    }
}

@Composable
fun NewsCard(photoUrl: String,
             newsTitle: String,
             onClickWebView: () -> Unit,
             modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {

        Column {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(photoUrl)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().padding(4.dp)
            )
            Text(
                text = newsTitle,
                modifier = Modifier.padding(4.dp)
            )
            IconButton(
                onClick = onClickWebView,
                modifier = Modifier
            ) {
                Icon(
                    imageVector = Icons.Filled.DoubleArrow,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }

    }
}

@Preview
@Composable
fun NewsListPreview() {
    NewsTheme {
//        NewsList()
    }
}

@Preview
@Composable
fun CatagoryListPreview() {
    NewsTheme {
        CatagoryList(catagories = listOf("all","national", "business",
            "sports", "world",
            "politics", "technology", "startup", "entertainment",
            "miscellaneous", "hatke", "science", "automobile"),
            {}
            )
    }
}