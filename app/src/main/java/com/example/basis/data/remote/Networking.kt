package com.example.basis.data.remote

import com.example.basis.utils.network.MyJsonConverter
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit

/*
Retrofit creator class
 */
object Networking {

    const val BASE_URL = "https://gist.githubusercontent.com/"

    fun create() : NetworkService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MyJsonConverter.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(NetworkService::class.java)
    }
}