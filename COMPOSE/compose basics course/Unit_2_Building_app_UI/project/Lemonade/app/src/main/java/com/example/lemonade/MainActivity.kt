package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            ),
                            title = {
                                Text(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .wrapContentSize(Alignment.Center),
                                    text = stringResource(id = R.string.app_name)
                                )
                            }
                        )
                    },
                    modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Lemonade(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Lemonade(modifier: Modifier = Modifier) {
    var result by rememberSaveable {
        mutableStateOf(1)
    }

    var tapToSqueeze: Int = (5..10).random()
    val imageResource: Int
    val textResource: Int
    val contentDescription: Int
    when (result) {
        1 -> {
            imageResource = R.drawable.lemon_tree
            textResource = R.string.lemon_tree
            contentDescription = R.string.lemon_tree_content_description
        }
        2 -> {
            imageResource = R.drawable.lemon_squeeze
            textResource = R.string.squeeze_it
            contentDescription = R.string.lemon_content_description
        }
        3 -> {
            imageResource = R.drawable.lemon_drink
            textResource = R.string.drink_it
            contentDescription = R.string.glass_of_lemonade_content_description
        }
        else -> {
            imageResource = R.drawable.lemon_restart
            textResource = R.string.start_again
            contentDescription = R.string.empty_glass_content_description
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    )
    {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = stringResource(id = contentDescription),
            modifier = Modifier.clickable(onClick = {
                if (result == 4) {
                    result = 1
                } else if (result == 2) {
                    tapToSqueeze--
                    if(tapToSqueeze <= 0){
                        result++
                    }
                } else {
                    result++
                }
            })
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = textResource),
            fontSize = 18.sp,
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LemonadeTheme {
        Lemonade()
    }
}