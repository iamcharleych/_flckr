package com.ic.flckr.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ic.flckr.BuildConfig
import com.ic.flckr.FlckrApp
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpCache(application: FlckrApp): Cache {
        val cacheSize = 5 * 1024 * 1024L // 5 MiB
        return Cache(application.cacheDir, cacheSize)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .disableHtmlEscaping()
            .create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache?): OkHttpClient {
        val clientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        clientBuilder.cache(cache)
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            clientBuilder.addInterceptor(httpLoggingInterceptor)
        }
        return clientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttpClient)
            .build()
    }

//    @Provides
//    @Singleton
//    fun provideRestClient(retrofit: Retrofit): RestClient<DataResponse<*>> {
//        return RetrofitRestClient(retrofit)
//    }
}