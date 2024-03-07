package com.example.mainactivity.search_module.data.models.response

import com.google.gson.annotations.SerializedName

data class ArtistResponse(
    @SerializedName("followers")
    val followers: ArtistItemFollowersModel? = null,

    @SerializedName("genres")
    val genres: List<String>? = null,

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("images")
    val images: List<ItemImage>? = null,

    @SerializedName("type")
    val type: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("popularity")
    val popularity: Int? = null
)
