package com.jackie.paging3demo.logic.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @User: machenglong
 * @Date: 2021/12/22
 * @Describe:
 */
object NetworkManager {

    private const val BASE_URL = "https://www.wanandroid.com"

    private val httpLoggingInterceptor =
        HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }

    private val client = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor).build()

    val retrofit: Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val rxRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    inline fun <reified T> createService(): T =
        retrofit.create(T::class.java)

    inline fun <reified T> createServiceByRx(): T =
        rxRetrofit.create(T::class.java)
}