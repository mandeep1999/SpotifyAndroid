package com.example.mainactivity.core.di

import android.content.Context
import com.example.mainactivity.core.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Provides appDataBase -> room through DI.
 */
@InstallIn(SingletonComponent::class)
@Module
object AppDataBaseModule {


    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getInstance(context)
}