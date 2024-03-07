package com.example.mainactivity.search_module.repositories

import com.example.mainactivity.core.database.AppDatabase
import com.example.mainactivity.core.utils.BaseRepo
import com.example.mainactivity.core.utils.Resource
import com.example.mainactivity.search_module.data.models.dtos.SearchRowComponentModel
import com.example.mainactivity.search_module.data.models.response.SearchResponse
import com.example.mainactivity.search_module.interfaces.SpotifyApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(
    private val spotifyApi: SpotifyApi,
    private val appDatabase: AppDatabase
) : BaseRepo() {


    suspend fun getSearchResults(searchText: String): Resource<SearchResponse> {
        val type = "album,artist,playlist,track,show,episode"
        return safeApiCall { spotifyApi.getSearchItems(searchText, type) }
    }

    suspend fun getRecentSearches(): List<SearchRowComponentModel> {
        return appDatabase.recentSearchesDAO.getTop5Searches()
    }

    suspend fun insertRecentSearchIntoDB(searchRowComponentModel: SearchRowComponentModel) {
        val oldEntries = getRecentSearches()
        if (oldEntries.size >= 5) {
            appDatabase.recentSearchesDAO.delete(oldEntries[0])
        }
        appDatabase.recentSearchesDAO.upsertRecentSearch(searchRowComponentModel)
    }
}