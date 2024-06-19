package com.example.businesscard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.businesscard.ui.theme.BusinessCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BusinessCardTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainScreen(
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        HeaderSection(
            image = painterResource(id = R.drawable.c),
            name = stringResource(id = R.string.full_name),
            role = stringResource(id = R.string.role),
            modifier = Modifier.weight(1f)
        )
        ContactInfoSection(
            phNumber = stringResource(id = R.string.ph_no),
            gmail = stringResource(id = R.string.gmail),
            address = stringResource(id = R.string.address),
//            modifier = Modifier.align(alignment = Arrangement.)
        )
    }
}

@Composable
fun HeaderSection(image: Painter, name: String, role: String, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {

        Surface(
//            shape = RoundedCornerShape(1000.dp)
        ) {
            Image(
                painter = image,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
        Text(
            text = name,
            color = Color(0xFF3ddc84),
            fontWeight = FontWeight.ExtraBold,
            style = TextStyle(
                fontStyle = FontStyle.Italic,
//                shadow = Shadow(
//                    color = Color.Black,
//                    offset = Offset(5f, 5f),
//                    blurRadius = 5f
//                ),
                fontFamily = FontFamily.Cursive
            ),
            modifier = Modifier.padding(12.dp)
        )
        Text(
            text = role,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.Gray,
                    offset = Offset(5f, 5f),
                    blurRadius = 5f
                ),
            ),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
        )
    }
}

@Composable
fun ContactInfoSection(phNumber: String, gmail: String, address: String, modifier: Modifier = Modifier) {
    Column (
        modifier = Modifier.padding(bottom = 20.dp)
    ){
        Row (
            modifier = modifier
                .padding(8.dp)
        ){
            Icon(
                imageVector = Icons.Rounded.Call,
                tint = Color(0xFF3ddc84),
                contentDescription = "phone number"
            )
            Text(
                text = phNumber,
                modifier = Modifier.padding(
                    start = 12.dp
                )
                )
        }
        Row (
            modifier = modifier.padding(8.dp)
        ){
            Icon(
                imageVector = Icons.Rounded.Email,
                tint = Color(0xFF3ddc84),
                contentDescription = "gmail")
            Text(
                text = gmail,
                modifier = Modifier.padding(
                    start = 12.dp
                )
            )
        }
        Row(
            modifier = modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Place,
                tint = Color(0xFF3ddc84),
                contentDescription = "Address"
            )
            Text(
                text = address,
                modifier = Modifier.padding(
                    start = 12.dp
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    BusinessCardTheme {
        MainScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderSectionPreview() {
    BusinessCardTheme {
        HeaderSection(
            image = painterResource(id = R.drawable.c),
            name = stringResource(id = R.string.full_name),
            role = stringResource(id = R.string.role)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ContactInfoSectionPreview() {
    BusinessCardTheme {
        ContactInfoSection(
            phNumber = stringResource(id = R.string.ph_no),
            gmail = stringResource(id = R.string.gmail),
            address = stringResource(id = R.string.address)
        )
    }
}

