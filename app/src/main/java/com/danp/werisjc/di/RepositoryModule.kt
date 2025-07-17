package com.danp.werisjc.di

import com.danp.werisjc.data.repository.PostRepositoryImpl
import com.danp.werisjc.data.repository.ServiceRepositoryImpl
import com.danp.werisjc.domain.repository.PostRepository
import com.danp.werisjc.domain.repository.ServiceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindServiceRepository(
        impl: ServiceRepositoryImpl
    ): ServiceRepository

    @Binds
    @Singleton
    abstract fun bindPostRepository(
        impl: PostRepositoryImpl
    ): PostRepository
}