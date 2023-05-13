package com.example.moviewithpaging.api

import com.example.moviewithpaging.utility.Constraints
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object MovieDBClient {

    fun getClient():MovieDBInterface{

        val requestInterceptor = Interceptor{
            val url = it.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", Constraints.apiKey)
                .build()

            val request = it.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor it.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constraints.movieUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieDBInterface::class.java)
    }
}

//.addCallAdapterFactory(RxJava3CallAdapterFactory.create())