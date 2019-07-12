package com.example.basis.data.remote

import com.example.basis.data.remote.response.CardResponse
import io.reactivex.Single
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface NetworkService {

    @GET(EndPoints.CARDS)
    fun getCardsFromWeb() : Single<CardResponse>
}