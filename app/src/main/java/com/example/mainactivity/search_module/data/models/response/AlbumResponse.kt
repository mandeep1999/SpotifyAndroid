package com.example.mainactivity.search_module.data.models.response

import com.google.gson.annotations.SerializedName

data class AlbumResponse(
    @SerializedName("artists")
    val artists: List<ArtistItemModel>? = null,

    @SerializedName("images")
    val images: List<ItemImage>? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("release_date")
    val releaseData: String? = null,

    @SerializedName("tracks")
    val tracks: TracksModel? = null,

)
