package com.example.artspace

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.artspace.ui.theme.ArtSpaceTheme
import kotlin.math.round

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ArtWork(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ArtWork(modifier: Modifier = Modifier) {
    var num by rememberSaveable {
        mutableStateOf(0)
    }
    val size = 3
    val artImage: Int
    val artDescription: Int
    val artAuthor: Int
    val artYear = R.string.year

    when (num) {
        0 -> {
            artImage = R.drawable.nice
            artDescription = R.string.title_one
            artAuthor = R.string.author_one
        }
        1 -> {
            artImage = R.drawable.mass
            artDescription = R.string.title_two
            artAuthor = R.string.author_two
        }
        else -> {
            artImage = R.drawable.blackandwhite
            artDescription = R.string.title_three
            artAuthor = R.string.author_three
        }
    }

    Column (
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .safeDrawingPadding()
            .fillMaxSize()
    ) {
        ArtWorkImage(artImage)
        ArtWorkDescription(
            artDescription = artDescription,
            artAuthor = artAuthor,
            artYear = artYear,
        )
        ArtWorkButtons(
            onValueDecrease = {
                num = (((num - 1) % size) + size) % size
                Log.i("main activity", "$num")
            },
            onValueIncrease = {
                num = (((num + 1) % size) + size) % size
                Log.i("main activity", "$num")
            }
        )
    }
}

@Composable
fun ArtWorkImage(
    @DrawableRes artImage: Int,
    modifier: Modifier = Modifier
) {
    Surface (
        modifier = Modifier.wrapContentSize().padding(20.dp),
        shadowElevation = 8.dp
    ){
        Image(
            painter = painterResource(id = artImage),
            contentDescription = null,
            modifier = Modifier.size(400.dp).padding(12.dp)
        )
    }
}

@Composable
fun ArtWorkDescription(
    @StringRes artDescription: Int,
    @StringRes artAuthor: Int,
    @StringRes artYear: Int,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(20.dp).background(color = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Text(
            text = stringResource(id = artDescription),
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(
                top = 8.dp
            )
            )
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = stringResource(id = artAuthor),
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(end = 10.dp)
            )
            Text(text = stringResource(id = artYear))
        }
    }

}

@Composable
fun ArtWorkButtons(
    onValueIncrease: () -> Unit,
    onValueDecrease: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = onValueDecrease,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        ) {
            Text("Previous")
        }
        Button(
            onClick = onValueIncrease,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        ) {
            Text("Next")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtWorkPreview() {
    ArtSpaceTheme {
        ArtWork()
    }
}