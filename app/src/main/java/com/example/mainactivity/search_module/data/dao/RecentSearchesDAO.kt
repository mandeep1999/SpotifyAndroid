package com.example.mainactivity.search_module.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mainactivity.search_module.data.models.dtos.SearchRowComponentModel

@Dao
interface RecentSearchesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertRecentSearch(searchRowComponentModel: SearchRowComponentModel)

    @Query("select * from search_items limit 5")
    fun getTop5Searches(): List<SearchRowComponentModel>

    @Delete
    fun delete(searchRowComponentModel: SearchRowComponentModel)
}