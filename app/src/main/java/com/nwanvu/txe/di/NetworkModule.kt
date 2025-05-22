package com.nwanvu.txe.di

import com.nwanvu.txe.BuildConfig
import com.nwanvu.txe.data.DataApiClient
import com.nwanvu.txe.data.network.ApiClient
import com.nwanvu.txe.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * This module is installed in the [SingletonComponent], ensuring that the provided instances are singletons
 * and shared across the application.
 *
 * @property baseUrl The base URL used by Retrofit for API requests.
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    private val baseUrl: String = "https://${Constants.API_DOMAIN}/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.connectTimeout(Constants.API_TIMEOUT, TimeUnit.SECONDS)
        okHttpClient.readTimeout(Constants.API_TIMEOUT, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(Constants.API_TIMEOUT, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            okHttpClient.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl).client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiClient(): ApiClient = DataApiClient()
}