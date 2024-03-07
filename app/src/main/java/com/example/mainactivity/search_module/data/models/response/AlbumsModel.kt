package com.example.mainactivity.search_module.data.models.response

import com.google.gson.annotations.SerializedName

data class AlbumsModel(
    @SerializedName("href")
    val href: String? = null,

    @SerializedName("items")
    val items: List<AlbumItemModel>? = null,

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

data class AlbumItemModel(
    @SerializedName("album_type")
    val albumType: String? = null,

    @SerializedName("artists")
    val artists: List<AlbumItemArtistItem>? = null,

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("images")
    val images: List<ItemImage>? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("release_date")
    val releaseDate: String? = null,

    @SerializedName("type")
    val type: String? = null,
)

data class AlbumItemArtistItem(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("type")
    val type: String? = null,
)


