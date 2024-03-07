package com.example.mainactivity.core.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.mainactivity.core.utils.TokenAuthenticator
import com.example.mainactivity.login_module.utils.ObjectGraph
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Developed by Mandeep Singh on 07-03-2024. The module is responsible for providing the same
 * instance of retrofit across the app. The module provides the instance using DI.
 */
@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {

    private const val BASE_URL = "https://api.spotify.com/v1/"

    @Singleton
    @Provides
    fun providesOkHttpClientBuilder(
        @ApplicationContext context: Context,
        tokenAuthenticator: TokenAuthenticator
    ): OkHttpClient.Builder {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor.Builder(context).build())
            .authenticator(tokenAuthenticator)
        okHttpClientBuilder.addInterceptor(
            Interceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .method(original.method, original.body)
                if (ObjectGraph.getOauth() != null) {
                    request.header(
                        "Authorization",
                        ObjectGraph.getOauth()?.tokenType + " " + ObjectGraph.getOauth()
                            ?.accessToken
                    )
                }
                chain.proceed(request.build())
            }
        )
        return okHttpClientBuilder
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClientBuilder: OkHttpClient.Builder): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClientBuilder.build())
        .build()
}
