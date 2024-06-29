package com.example.news

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.news.screens.NewsScreen
import com.example.news.screens.NewsSreen
import com.example.news.screens.WebViewScreen
import com.example.news.ui.theme.NewsTheme
import com.example.news.viewmodel.NewsViewModel

@Composable
fun NewsApp(
    newsViewModel: NewsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController,
        startDestination = NewsSreen.NEWS_SCREEN.name,
        modifier = modifier
    ) {

        composable(
            route = NewsSreen.NEWS_SCREEN.name
        ){
            NewsScreen(newsViewModel = newsViewModel)
        }

        composable(
            route = NewsSreen.WEBVIEW_SCREEN.name
        ){
            WebViewScreen()
        }

    }

}

@Preview(showBackground = true)
@Composable
fun NewsAppPreview() {
    NewsTheme {
        NewsApp()
    }
}