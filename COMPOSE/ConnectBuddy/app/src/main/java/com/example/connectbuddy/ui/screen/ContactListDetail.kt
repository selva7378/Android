package com.example.connectbuddy.screen

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.connectbuddy.viewmodel.ContactViewModel
import com.example.example.Results

@Composable
fun ContactListDetail(
    contactList: List<Results>,
    onContactSelected: (Results) -> Unit,
    contactViewModel: ContactViewModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        ContactList(onContactSelected = onContactSelected, contactResultList = contactList, Modifier.weight(1f))
        ContactDetailScreen(contactViewModel = contactViewModel, Modifier.weight(1f))
    }
}