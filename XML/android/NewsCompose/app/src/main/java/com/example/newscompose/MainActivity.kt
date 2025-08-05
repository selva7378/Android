package com.example.newscompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newscompose.ui.theme.NewsComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NewsScreen(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun NewsScreen(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun NewsList() {
    LazyColumn {

    }
}

@Composable
fun categoryList() {

}

@Composable
fun categoryCard() {
    val paddingModifier  = Modifier.padding(10.dp)
    Card( modifier = paddingModifier) {
        Text(text = "Simple Card with elevation",
            modifier = paddingModifier)
    }
}

@Composable
fun newsCard(){

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NewsComposeTheme {
        NewsScreen("Android")
    }
}

@Preview(showBackground = true)
@Composable
fun categoryCardPreview() {
    categoryCard()
}