package com.example.messenger.di

import android.app.Application
import com.example.messenger.data.MessengerRepository
import com.example.messenger.data.MessengerRepositoryImpl
import com.example.messenger.service.EmailServiceImpl
import com.example.messenger.service.GoogleServiceImpl
import com.example.messenger.service.MessengerFireBaseDatabase
import com.example.messenger.service.model.EmailService
import com.example.messenger.service.model.GoogleService
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    @Provides
//    @Singleton
//    fun provideMessengerDatabase(application: Application): MessengerDatabase =
//        Room.databaseBuilder(
//            application,
//            MessengerDatabase::class.java,
//            "messenger_db"
//        )
//            .build()

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): DatabaseReference = Firebase.database.reference

    @Provides
    @Singleton
    fun provideMessengerFireBaseDatabase(reference: DatabaseReference): MessengerFireBaseDatabase =
        MessengerFireBaseDatabase(reference)

    @Provides
    @Singleton
    fun provideSignInClient(application: Application): SignInClient {
        return Identity.getSignInClient(application)
    }

    @Provides
    @Singleton
    fun provideEmailService(): EmailService = EmailServiceImpl()

    @Provides
    @Singleton
    fun provideGoogleService(
        application: Application,
        signInClient: SignInClient
    ): GoogleService =
        GoogleServiceImpl(application, signInClient)

    @Provides
    @Singleton
    fun provideRepository(
        emailService: EmailService,
        googleService: GoogleService,
        messengerFireBaseDatabase: MessengerFireBaseDatabase
    ): MessengerRepository =
        MessengerRepositoryImpl(emailService, googleService, messengerFireBaseDatabase)
}