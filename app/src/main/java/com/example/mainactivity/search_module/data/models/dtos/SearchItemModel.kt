package com.example.mainactivity.search_module.data.models.dtos

import com.google.gson.annotations.SerializedName

sealed class SearchItemModel {

    class SearchHeaderClass(
        @SerializedName("title_text")
        val titleText: String? = null
    ): SearchItemModel()
}