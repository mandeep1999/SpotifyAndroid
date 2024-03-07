package com.example.mainactivity.search_module.data.models.response

import com.google.gson.annotations.SerializedName

data class ShowsModel(
    @SerializedName("href")
    val href: String? = null,

    @SerializedName("items")
    val items: List<ShowItemModel>? = null,

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

data class ShowItemModel(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("publisher")
    val publisher: String? = null,

    @SerializedName("images")
    val images: List<ItemImage>? = null,

    @SerializedName("show")
    val type: String? = null
)
