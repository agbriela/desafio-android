package com.example.desafiogabriela.model

import com.google.gson.annotations.SerializedName

data class ItemPullrequest(

    @SerializedName("user") val owner: Owner,
    @SerializedName("title") val name: String,
    @SerializedName("body") val description: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("html_url") val html: String
)
