package com.example.intentapp

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.intentapp.ui.theme.IntentAppTheme

class BroadCastActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntentAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ExplicitIntent(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

private fun shareText(context: Context, text: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}


@Composable
private fun ExplicitIntent(modifier: Modifier = Modifier){
    val context = LocalContext.current
    var text by rememberSaveable {
        mutableStateOf("")
    }
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column {
            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
            },
                label = {
                    Text(text = "type")
                }
            )
            ElevatedButton(onClick = {
                shareText(context, text)
            }) {
                Text(text = "SHARE")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ExplicitIntentPreview(){
    ExplicitIntent()
}
