package com.example.connectbuddy.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.connectbuddy.ContactScreen
import com.example.connectbuddy.R
import com.example.connectbuddy.viewmodel.ContactViewModel

@Composable
fun ContactDetailScreen(
    contactViewModel: ContactViewModel,
    modifier: Modifier = Modifier
) {
    val selectedContact = contactViewModel.selectedContact.collectAsState()

    Box(
        modifier = modifier.background(color = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            HeaderSection(
                image = selectedContact.value?.picture?.large ?: "",
                name = (selectedContact.value?.name?.first + " " + selectedContact.value?.name?.last),
            )
            ContactInfoSection(
                phNumber = selectedContact.value?.phone ?: "",
                gmail = selectedContact.value?.email ?: "",
                gender = selectedContact.value?.gender ?: "",
            )
        }
    }
}


@Composable
fun HeaderSection(image: String, name: String, modifier: Modifier = Modifier) {
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
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.ExtraBold,
            style = TextStyle(
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.Cursive,
                fontSize = 32.sp
            ),
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
fun ContactInfoSection(
    phNumber: String,
    gmail: String,
    gender: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.padding(bottom = 20.dp)
    ) {
        Row(
            modifier = modifier
                .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Call,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = "phone number"
            )
            Text(
                text = phNumber,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(
                    start = 12.dp,
                )
            )
        }
        Row(
            modifier = modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Email,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = "gmail",
            )
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
                painter = painterResource(id = R.drawable.male_and_female),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = "Gender",
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = gender,
                modifier = Modifier.padding(
                    start = 12.dp
                )
            )
        }
    }
}


