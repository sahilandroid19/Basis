package com.example.basis.data.remote.response

import com.example.basis.data.model.Card
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/*
Data Response class for Retrofit
 */
data class CardResponse(
    @Expose
    @SerializedName("data")
    var cards: List<Card>
)