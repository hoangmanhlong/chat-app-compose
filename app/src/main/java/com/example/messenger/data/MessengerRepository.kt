package com.example.messenger.data

import com.example.messenger.service.MessengerFireBaseDatabase
import com.example.messenger.service.model.EmailService
import com.example.messenger.service.model.GoogleService

interface MessengerRepository {
    val emailService: EmailService
    val googleService: GoogleService
    val messengerFireBaseDatabase: MessengerFireBaseDatabase
}

class MessengerRepositoryImpl(
    override val emailService: EmailService,
    override val googleService: GoogleService,
    override val messengerFireBaseDatabase: MessengerFireBaseDatabase
) : MessengerRepository