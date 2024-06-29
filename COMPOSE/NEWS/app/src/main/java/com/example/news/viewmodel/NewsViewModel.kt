package com.example.news.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.news.network.Data
import com.example.news.network.NewsData
import com.example.news.repository.NewsApiRepository
import com.example.news.repository.NewsDbRepository
import com.example.news.roomdb.News
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NewsViewModel(
    private val newsApiRepository: NewsApiRepository,
    private val newsDbRepository: NewsDbRepository
) : ViewModel() {

    var allNewsList: StateFlow<List<News>> = MutableStateFlow(listOf())

    val apiCatagoryList: List<String> = listOf(
        "all", "national", "business",
        "sports", "world",
        "politics", "technology", "startup", "entertainment",
        "miscellaneous", "hatke", "science", "automobile"
    )

    init {
        getNewsFromApi()
        getAllNews()
    }


    fun getNewsFromApi() {
        viewModelScope.launch {
            apiCatagoryList.forEach { category ->
                val newsData = newsApiRepository.getNews(category)
                insert(newsData)
            }
        }
    }

    suspend fun insert(news: NewsData) {
        news.data.forEach { perNews ->
            newsDbRepository.insert(
                News(
                    category = news.category,
                    author = perNews.author,
                    content = perNews.content,
                    date = perNews.date,
                    id = perNews.id,
                    imageUrl = perNews.imageUrl,
                    readMoreUrl = perNews.readMoreUrl,
                    time = perNews.time,
                    title = perNews.title,
                    url = perNews.url,
                    success = news.success.toString()
                )
            )
        }
    }

    fun getAllNews() {
        allNewsList = newsDbRepository.getAll().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    fun getNewsByCategory(category: String) {
        if (category.equals("all")){
            getAllNews()
        }else{
            allNewsList = newsDbRepository.getAllByCategory(category).stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
        }
    }
}