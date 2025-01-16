package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.di

import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository.AuthenticationRepository
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository.AuthenticationRepositoryImpl
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository.MessagingRepository
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository.MessagingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthenticationRepository(
        impl: AuthenticationRepositoryImpl
    ) : AuthenticationRepository


    @Binds
    @Singleton
    abstract fun bindMessagingRepository (
        impl: MessagingRepositoryImpl
    ) : MessagingRepository

}