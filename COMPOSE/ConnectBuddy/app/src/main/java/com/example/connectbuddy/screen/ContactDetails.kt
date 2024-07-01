package com.example.connectbuddy.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.connectbuddy.R

@Composable
fun ContactDetailScreen(
    id: String,
    name: String,
    gender: String,
    email: String,
    phone: String,
    cell: String,
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ){
            HeaderSection(
                image = imageUrl,
                name = id,
                role = name,
                modifier = Modifier.weight(1f)
            )
            ContactInfoSection(
                phNumber = phone,
                gmail = email,
                gender = gender,
            )
        }

}



@Composable
fun HeaderSection(image: String, name: String, role: String, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {

        Surface(
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(image)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .padding(4.dp)
                    .clip(CircleShape)
            )
        }
        Text(
            text = name,
            color = Color(0xFF3ddc84),
            fontWeight = FontWeight.ExtraBold,
            style = TextStyle(
                fontStyle = FontStyle.Italic,
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
fun ContactInfoSection(phNumber: String, gmail: String, gender: String, modifier: Modifier = Modifier) {
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
            Icon(painter = painterResource(id = R.drawable.male_and_female),
                tint = Color(0xFF3ddc84),
                contentDescription = "Gender",
                modifier = Modifier.size(30.dp))
            Text(
                text = gender,
                modifier = Modifier.padding(
                    start = 12.dp
                )
            )
        }
    }
}


//@Composable
//fun detailsCard(label: String, value: String){
//    ElevatedCard(
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween,
//        ) {
//            Text(text = label,
//                style = MaterialTheme.typography.headlineLarge,
//               )
//            Text(text = ":",
//                style = MaterialTheme.typography.displaySmall,
//                modifier = Modifier
//               )
//            Text(text = value,
//                style = MaterialTheme.typography.headlineSmall,
//                )
//        }
//    }
//}


