package com.example.connectbuddy.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoubleArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.connectbuddy.ContactBuddy
import com.example.connectbuddy.ContactScreen
import com.example.connectbuddy.R
import com.example.connectbuddy.ui.theme.ConnectBuddyTheme
import com.example.connectbuddy.viewmodel.ContactViewModel
import com.example.example.Results
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun ContactHomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    contactViewModel: ContactViewModel,
) {
    val contact = contactViewModel.contactData.collectAsState()
    ContactList(contact.value.results, navController, modifier)
}


@Composable
fun ContactList(
    contactResultList: List<Results>,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(contactResultList) { perContact ->
            ContactCard(userContact = perContact, navController)
        }
    }
}

@Composable
fun dummyCard(userContact: Results) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "${userContact.name?.first}")
    }
}

@Composable
fun ContactCard(
    userContact: Results,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
    ) {
        Column(
            modifier = Modifier
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_small)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                UserIcon(userContact.picture?.large!!)
                UserInformation(
                    userContact.name?.first + " " + userContact.name?.last,
                    userContact.phone!!,
                )
                Spacer(modifier = Modifier.weight(1f))
                ContactDetailsButton(onClick = {
                    val imageUrl =
                        URLEncoder.encode(
                            userContact.picture?.large,
                            StandardCharsets.UTF_8.toString()
                        )
                    navController
                        .navigate("${ContactScreen.BUDDY_DETAILS.name}/${userContact.id?.name}/${userContact.name?.first}/${userContact.gender}/${userContact.email}/${userContact.phone}/${userContact.cell}/${imageUrl}")
                })
            }
        }

    }
}


@Composable
fun UserIcon(
    userIconUrl: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(userIconUrl)
            .crossfade(true)
            .build(),
        error = painterResource(R.drawable.ic_broken_image),
        placeholder = painterResource(R.drawable.loading_img),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(50.dp)
            .padding(4.dp)
            .clip(CircleShape)
    )
}

@Composable
fun UserInformation(
    userName: String,
    userNumber: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "$userName",
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small))
        )
        Text(
            text = "$userNumber",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun ContactDetailsButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
    ) {
        Icon(
            imageVector = Icons.Filled.DoubleArrow,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}



@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ConnectBuddyTheme {
        ContactBuddy()
    }
}

@Preview(showBackground = true)
@Composable
fun ContactCardPreview() {
    ConnectBuddyTheme {
    }
}

