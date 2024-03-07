package com.example.mainactivity.search_module.data.models.response

import com.google.gson.annotations.SerializedName

data class ItemImage(
    @SerializedName("height")
    val height: Int? = null,

    @SerializedName("url")
    val url: String? = null,

    @SerializedName("width")
    val width: Int? = null
)
