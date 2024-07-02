package com.example.connectbuddy.screen

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.connectbuddy.viewmodel.ContactViewModel

@Composable()
fun ListDetailRoute(
    isExpandedWindowSize: Boolean,
    contactViewModel: ContactViewModel,
    modifier: Modifier = Modifier
) {

    val contact = contactViewModel.contactData.collectAsState()
    val selectedContact = contactViewModel.selectedContact.collectAsState()
    if (isExpandedWindowSize) {
        ContactListDetail(
            contactList = contact.value.results,
            onContactSelected = contactViewModel.onContactSelected,
            contactViewModel,
            modifier
        )
    } else {
        if (selectedContact.value != null) {
            ContactDetailScreen(contactViewModel, modifier)
            BackHandler {
                contactViewModel.onItemBackPressed()
                Log.i("backlauncher", "bact active ${contactViewModel.selectedContact.value}")
            }

        } else {
            ContactList(
                onContactSelected = contactViewModel.onContactSelected,
                contact.value.results,
                modifier
            )
        }
    }
}
