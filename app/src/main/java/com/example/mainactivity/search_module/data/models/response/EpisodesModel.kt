package com.example.mainactivity.search_module.data.models.response

import com.google.gson.annotations.SerializedName

data class EpisodesModel(
    @SerializedName("href")
    val href: String? = null,

    @SerializedName("items")
    val items: List<EpisodeItemModel>? = null,

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

data class EpisodeItemModel(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("type")
    val type: String? = null,

    @SerializedName("duration_ms")
    val durationMS: Long? = null,

    @SerializedName("images")
    val images: List<ItemImage>? = null
)
