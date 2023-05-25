package com.carefer.task.di.module


import com.carefer.core.data.local.StorageManager
import com.carefer.core.data.local.StorageManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindStorageManager(storageManagerImpl: StorageManagerImpl): StorageManager
}