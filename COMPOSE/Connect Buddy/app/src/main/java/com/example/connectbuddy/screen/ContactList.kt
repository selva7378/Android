package com.example.connectbuddy.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoubleArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.connectbuddy.R
import com.example.connectbuddy.nr.theme.ContactBuddyTheme
import com.example.connectbuddy.viewmodel.ContactUiState
import com.example.connectbuddy.viewmodel.ContactViewModel
import com.example.example.Results


@Composable
fun ContactHomeScreen(
    isBookPosture: Boolean,
    modifier: Modifier = Modifier,
    contactViewModel: ContactViewModel,
) {

    var isExpandedWindowSize = isBookPosture

    when (contactViewModel.contactUiState) {
        is ContactUiState.Loading -> LoadingScreen(Modifier.fillMaxSize())
        is ContactUiState.Error -> ErrorScreen(
            retryAction = { contactViewModel.getNewsFromApi() },
            Modifier.fillMaxSize()
        )

        is ContactUiState.Success -> ListDetailRoute(
            isExpandedWindowSize = isExpandedWindowSize,
            contactViewModel = contactViewModel,
            modifier
        )
    }
}


@Composable
fun ContactList(
    listState: LazyListState,
    onContactSelected: (Results) -> Unit,
    contactResultList: List<Results>,
    modifier: Modifier = Modifier
) {

    Box(modifier = modifier){
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(items = contactResultList) { perContact ->
                ContactCard(onContactSelected, userContact = perContact, Modifier)
            }
        }
    }
}

@Composable
fun ContactCard(
    onContactClicked: (Results) -> Unit,
    userContact: Results,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        onClick = { onContactClicked(userContact) }
    ) {

        Row(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_small)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            UserIcon(userContact.picture?.large!!)
            UserInformation(
                userContact.name?.first + " " + userContact.name?.last,
                userContact.phone!!,
            )
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
    Column(modifier = Modifier.padding(start = 40.dp)) {
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
    ContactBuddyTheme {
    }
}

@Preview(showBackground = true)
@Composable
fun ContactCardPreview() {
    ContactBuddyTheme {
    }
}

