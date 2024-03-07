package com.example.mainactivity.search_module.data.models.response

import com.google.gson.annotations.SerializedName

data class ArtistsModel(
    @SerializedName("href")
    val href: String? = null,

    @SerializedName("items")
    val items: List<ArtistItemModel>? = null,

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

data class ArtistItemModel(
    @SerializedName("followers")
    val followers: ArtistItemFollowersModel? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("genres")
    val genres: List<String>? = null,

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("type")
    val type: String? = null,

    @SerializedName("images")
    val images: List<ItemImage>? = null
)

data class ArtistItemFollowersModel(
    @SerializedName("href")
    val href: String? = null,

    @SerializedName("total")
    val total: String? = null
)
