package com.example.connectbuddy

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.connectbuddy.screen.ContactDetailScreen
import com.example.connectbuddy.screen.ContactHomeScreen
import com.example.connectbuddy.viewmodel.ContactViewModel

val ROUTE_USER_DETAILS = "${ContactScreen.BUDDY_DETAILS.name}?contact={contact}"

enum class ContactScreen {
    CONTACT_BUDDY,
    BUDDY_DETAILS,
    CONTACT_BUDDY_DETAILS
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactBuddy(
    isBookPosture: Boolean,
    contactViewModel: ContactViewModel = viewModel(factory = AndroidViewModelProvider.Factory),
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ContactAppBar(
                scrollBehavior = scrollBehavior,
                currentScreen = contactViewModel.topAppBarName.value,
                canNavigateBack = contactViewModel.topAppBarIcon,
                onClick = {contactViewModel.onContactSelected(null)
                    if (contactViewModel.topAppBarIcon == true){
                        contactViewModel.topAppBarIcon = false
                    }
                }
            )
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ContactScreen.CONTACT_BUDDY.name,
            modifier = modifier
        ) {

            composable(
                route = ContactScreen.CONTACT_BUDDY.name
            ) {
                ContactHomeScreen(
                    isBookPosture,
                    contactViewModel = contactViewModel,
                    modifier = modifier.padding(innerPadding)
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    currentScreen: String,
    canNavigateBack: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = currentScreen,
                color = MaterialTheme.colorScheme.onPrimary,
                style = TextStyle(
                    fontStyle = FontStyle.Italic,
                    fontFamily = FontFamily.Cursive,
                    fontSize = 32.sp
                )
            )
                },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(
                    onClick = onClick,
                    colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.onPrimary)
                    ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "navigate back"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(MaterialTheme.colorScheme.primary)
    )
}

