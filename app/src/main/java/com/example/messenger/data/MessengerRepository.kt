package com.example.messenger.data

import com.example.messenger.service.model.EmailService
import com.example.messenger.service.model.GoogleService

interface MessengerRepository {
    val emailService: EmailService
    val googleService: GoogleService
}

class MessengerRepositoryImpl(
    override val emailService: EmailService,
    override val googleService: GoogleService
) : MessengerRepository