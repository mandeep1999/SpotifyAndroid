package com.example.mainactivity.search_module.data.models.dtos


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mainactivity.search_module.data.models.enums.IconShape
import com.google.gson.annotations.SerializedName

@Entity(tableName = "search_items")
data class SearchRowComponentModel(
    @ColumnInfo(name = "title_text")
    @SerializedName("title_text")
    var titleText: String? = null,

    @ColumnInfo(name = "description_text")
    @SerializedName("description_text")
    var descriptionText: String? = null,

    @ColumnInfo(name = "icon")
    @SerializedName("icon")
    var icon: Icon? = null,

    @ColumnInfo(name = "icon_shape")
    @SerializedName("icon_shape")
    var iconShape: IconShape? = null,

    @ColumnInfo(name = "end_icon")
    @SerializedName("end_icon")
    var endIcon: Icon? = null,

    @ColumnInfo(name = "id")
    @PrimaryKey
    @SerializedName("id")
    var id: String,

    @ColumnInfo(name = "type")
    @SerializedName("type")
    var type: String,
)

data class Icon(
    @SerializedName("drawable")
    val drawable: String? = null,

    @SerializedName("url")
    val url: String? = null
)
