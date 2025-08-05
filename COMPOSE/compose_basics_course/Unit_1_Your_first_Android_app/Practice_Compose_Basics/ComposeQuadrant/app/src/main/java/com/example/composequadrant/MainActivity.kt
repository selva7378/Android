package com.example.composequadrant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composequadrant.ui.theme.ComposeQuadrantTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeQuadrantTheme {
                ComposeGuide()
            }
        }
    }
}

@Composable
fun ComposeGuide(modifier: Modifier = Modifier){
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = modifier.weight(
                weight = 1f,
                fill = true
            )
        ) {
            ComposeCard(
                composeTitle = stringResource(id = R.string.text_compose),
                composeDescription = stringResource(id = R.string.text_compose_description),
                modifier = Modifier
                    .weight(1f)
                    .background(Color(0xFFEADDFF))
                    .padding(16.dp)
            )
            ComposeCard(
                composeTitle = stringResource(id = R.string.image_compose),
                composeDescription = stringResource(id = R.string.image_compose_description),
                modifier = Modifier
                    .weight(1f)
                    .background(Color(0xFFD0BCFF))
                    .padding(16.dp)
            )
        }
        Row(
            modifier = modifier.weight(
                weight = 1f,
                fill = true
                )
        ) {
            ComposeCard(
                composeTitle = stringResource(id = R.string.row_compose),
                composeDescription = stringResource(id = R.string.row_compose_description),
                modifier = Modifier
                    .weight(1f)
                    .background(Color(0xFFB69DF8))
                    .padding(16.dp)
            )
            ComposeCard(
                composeTitle = stringResource(id = R.string.column_compose),
                composeDescription = stringResource(id = R.string.column_compose_description),
                modifier = Modifier
                    .weight(1f)
                    .background(Color(0xFFF6EDFF))
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun ComposeCard(
    composeTitle: String,
    composeDescription: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxHeight()
    ) {
        Text(
            text = composeTitle,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        Text(
            text = composeDescription,
            textAlign = TextAlign.Justify,
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeQuadrantTheme {
        ComposeGuide()
    }
}