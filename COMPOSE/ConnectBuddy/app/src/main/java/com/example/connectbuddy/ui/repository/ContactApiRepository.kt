package com.example.connectbuddy.repository

import com.example.connectbuddy.network.ContactApiService
import com.example.connectbuddy.network.dataclass.Contact

interface ContactApiRepository {
    suspend fun getContacts(): Contact
}

class NetworkContactsApiRepository(
    private val contactApiService: ContactApiService
): ContactApiRepository {
    override suspend fun getContacts(): Contact = contactApiService.getData()
}

