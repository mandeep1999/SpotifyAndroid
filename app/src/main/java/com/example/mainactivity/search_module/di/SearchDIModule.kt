package com.example.mainactivity.search_module.di

import com.example.mainactivity.search_module.interfaces.SpotifyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SearchDIModule {

    @Singleton
    @Provides
    fun provideActionBottomSheetApi(retrofit: Retrofit): SpotifyApi =
        retrofit.create(SpotifyApi::class.java)
}