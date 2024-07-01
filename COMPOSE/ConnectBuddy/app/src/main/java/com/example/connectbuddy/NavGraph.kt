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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
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

@Composable
fun ContactBuddy(
    contactViewModel: ContactViewModel = viewModel(factory = AndroidViewModelProvider.Factory),
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = ContactScreen.valueOf(
        backStackEntry?.destination?.route?.substringBefore("/") ?: ContactScreen.CONTACT_BUDDY.name
    )

    Scaffold(
        topBar = {
            ContactAppBar(
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
                    navController = navController,
                    contactViewModel = contactViewModel,
                    modifier = modifier.padding(innerPadding)
                )
            }

            composable(
                route = "${ContactScreen.BUDDY_DETAILS.name}/{id}/{name}/{gender}/{email}/{phone}/{cell}/{imageUrl}",
                arguments = listOf(
                    navArgument(name = "id") { type = NavType.StringType },
                    navArgument(name = "name") { type = NavType.StringType },
                    navArgument(name = "gender") { type = NavType.StringType },
                    navArgument(name = "email") { type = NavType.StringType },
                    navArgument(name = "phone") { type = NavType.StringType },
                    navArgument(name = "cell") { type = NavType.StringType },
                    navArgument(name = "imageUrl") { type = NavType.StringType },
                )
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id") ?: ""
                val name = backStackEntry.arguments?.getString("name") ?: ""
                val gender = backStackEntry.arguments?.getString("gender") ?: ""
                val email = backStackEntry.arguments?.getString("email") ?: ""
                val phone = backStackEntry.arguments?.getString("phone") ?: ""
                val cell = backStackEntry.arguments?.getString("cell") ?: ""
                val imageUrl = backStackEntry.arguments?.getString("imageUrl") ?: ""
                ContactDetailScreen(
                    id = id,
                    name = name,
                    gender = gender,
                    email = email,
                    phone = phone,
                    cell = cell,
                    imageUrl = imageUrl
                )
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactAppBar(
    currentScreen: ContactScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
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

