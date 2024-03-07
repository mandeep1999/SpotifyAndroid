package com.example.mainactivity.search_module.data.models.response

import com.google.gson.annotations.SerializedName

data class PlayListsModel(
    @SerializedName("href")
    val href: String? = null,

    @SerializedName("items")
    val items: List<PlayListItemModel>? = null,

    @SerializedName("limit")
    val limit: Int? = null,

    @SerializedName("next")
    val next: String? = null,

    @SerializedName("offset")
    val offset: Int? = null,

    @SerializedName("previous")
    val previous: String? = null,

    @SerializedName("total")
    val total: Int? = null
)

data class PlayListItemModel(
    @SerializedName("description")
    val description: String? = null,

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("images")
    val image: List<ItemImage>? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("type")
    val type: String? = null

)
