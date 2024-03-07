package com.example.mainactivity.core.database.converters

import androidx.room.TypeConverter
import com.example.mainactivity.search_module.data.models.dtos.Icon
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class IconTypeConverter {

    @TypeConverter
    fun fromIcon(icon: Icon): String {
        val gson: Gson = Gson()
        return gson.toJson(icon)
    }

    @TypeConverter
    fun toIcon(icon: String): Icon {
        val listType = object : TypeToken<Icon>() {}.type
        return Gson().fromJson(icon, listType)
    }
}