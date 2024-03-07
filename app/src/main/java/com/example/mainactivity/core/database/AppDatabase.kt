package com.example.mainactivity.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mainactivity.core.database.converters.IconShapeConverter
import com.example.mainactivity.core.database.converters.IconTypeConverter
import com.example.mainactivity.search_module.data.dao.RecentSearchesDAO
import com.example.mainactivity.search_module.data.models.dtos.SearchRowComponentModel

@Database(
    entities = [
        SearchRowComponentModel::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(
    IconTypeConverter::class,
    IconShapeConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract val recentSearchesDAO: RecentSearchesDAO


    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "spotify_clone_database",
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}