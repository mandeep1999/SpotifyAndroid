package com.example.mainactivity.core.database.converters

import androidx.room.TypeConverter
import com.example.mainactivity.search_module.data.models.enums.IconShape

class IconShapeConverter {

    @TypeConverter
    fun toIconShape(value: String) = enumValueOf<IconShape>(value)

    @TypeConverter
    fun fromIconShape(value: IconShape) = value.name
}