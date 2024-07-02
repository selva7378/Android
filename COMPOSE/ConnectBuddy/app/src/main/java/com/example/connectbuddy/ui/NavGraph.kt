package com.example.connectbuddy

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
    BUDDY_DETAILS
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactBuddy(
    windowSize: WindowSizeClass,
    contactViewModel: ContactViewModel = viewModel(factory = AndroidViewModelProvider.Factory),
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = ContactScreen.valueOf(
        backStackEntry?.destination?.route ?: ContactScreen.CONTACT_BUDDY.name
    )

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ContactAppBar(
                scrollBehavior = scrollBehavior,
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
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
                    windowSize,
                    navController = navController,
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
    currentScreen: ContactScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = { Text(text = currentScreen.name) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "navigate back"
                    )
                }
            }
        }
    )
}

