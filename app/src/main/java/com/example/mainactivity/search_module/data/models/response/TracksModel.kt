package com.example.mainactivity.search_module.data.models.response

import com.google.gson.annotations.SerializedName

data class TracksModel(
    @SerializedName("href")
    val href: String? = null,

    @SerializedName("items")
    val items: List<TrackItemModel>? = null,

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

data class TrackItemModel(

    @SerializedName("album")
    val album: AlbumItemModel? = null,

    @SerializedName("artists")
    val artists: List<ArtistItemModel>? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("type")
    val type: String? = null,

    @SerializedName("id")
    val id: String? = null,

    )
