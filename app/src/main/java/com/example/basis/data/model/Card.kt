package com.example.basis.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/*
Model class for Cards
 */
data class Card(
    @Expose
    @SerializedName("id")
    var id: String,

    @Expose
    @SerializedName("text")
    var name: String
)