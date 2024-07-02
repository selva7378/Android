package com.example.connectbuddy.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.connectbuddy.ContactScreen
import com.example.connectbuddy.viewmodel.ContactViewModel

@Composable()
fun ListDetailRoute(
    isExpandedWindowSize: Boolean,
    contactViewModel: ContactViewModel,
    modifier: Modifier = Modifier
) {

    val scrollState = rememberLazyListState()
    val contact = contactViewModel.contactData.collectAsState()
    val selectedContact = contactViewModel.selectedContact.collectAsState()
    if (isExpandedWindowSize) {
        contactViewModel.topAppBarName.value = ContactScreen.CONTACT_BUDDY_DETAILS.name
        if (contactViewModel.topAppBarIcon == true) {
            contactViewModel.topAppBarIcon = false
        }
        ContactListDetail(
            scrollState,
            contactList = contact.value.results,
            onContactSelected = contactViewModel.onContactSelected,
            contactViewModel,
            modifier
        )
    } else {
        if (selectedContact.value != null) {
            contactViewModel.topAppBarName.value = ContactScreen.BUDDY_DETAILS.name
            contactViewModel.topAppBarIcon = true
            ContactDetailScreen(contactViewModel, modifier)
            BackHandler {
                contactViewModel.topAppBarIcon = false
                contactViewModel.onItemBackPressed()
                contactViewModel.topAppBarName.value = ContactScreen.CONTACT_BUDDY.name
            }
        } else {
            contactViewModel.topAppBarName.value = ContactScreen.CONTACT_BUDDY.name
            ContactList(
                scrollState,
                onContactSelected = contactViewModel.onContactSelected,
                contact.value.results,
                modifier
            )
        }
    }
}
