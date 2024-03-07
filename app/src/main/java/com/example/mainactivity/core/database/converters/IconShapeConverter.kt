package com.example.mainactivity.core.database.converters

import androidx.room.TypeConverter
import com.example.mainactivity.search_module.data.models.enums.IconShape

/**
 * In order to store object into room, we first have to convert it into string,
 * and vice versa while reading from ROOM.
 */
class IconShapeConverter {

    @TypeConverter
    fun toIconShape(value: String) = enumValueOf<IconShape>(value)

    @TypeConverter
    fun fromIconShape(value: IconShape) = value.name
}