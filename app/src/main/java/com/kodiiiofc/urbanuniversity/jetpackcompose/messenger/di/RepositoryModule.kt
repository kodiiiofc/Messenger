package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.di

import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository.AuthenticationRepository
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository.AuthenticationRepositoryImpl
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository.ContactsRepository
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository.ContactsRepositoryImpl
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository.MessagingRepository
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository.MessagingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthenticationRepository(
        impl: AuthenticationRepositoryImpl
    ) : AuthenticationRepository

    @Binds
    abstract fun bindMessagingRepository (
        impl: MessagingRepositoryImpl
    ) : MessagingRepository

    @Binds
    abstract fun bindContactsRepository (
        impl: ContactsRepositoryImpl
    ) : ContactsRepository

}