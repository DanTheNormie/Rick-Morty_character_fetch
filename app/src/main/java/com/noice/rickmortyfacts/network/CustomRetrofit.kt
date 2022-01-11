package com.noice.rickmortyfacts.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object CustomRetrofit {

    val Instance by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(httpClient)
            .build()
            .create(RickAndMortyService::class.java)
    }

    val baseUrl = "https://rickandmortyapi.com/api/"


    val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    val loggingInterceptor =
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

    val httpClient =
        OkHttpClient().newBuilder()
            .addInterceptor(loggingInterceptor)
            .build()

}