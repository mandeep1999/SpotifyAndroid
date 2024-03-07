package com.example.mainactivity.search_module.repositories

import com.example.mainactivity.core.utils.BaseRepo
import com.example.mainactivity.core.utils.Resource
import com.example.mainactivity.search_module.data.models.response.AlbumResponse
import com.example.mainactivity.search_module.data.models.response.ArtistResponse
import com.example.mainactivity.search_module.interfaces.SpotifyApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailsRepository @Inject constructor(
    private val spotifyApi: SpotifyApi
) : BaseRepo() {


    suspend fun getAlbumDetails(id: String): Resource<AlbumResponse> {
        return safeApiCall { spotifyApi.getAlbumDetails(id) }
    }

    suspend fun getArtistDetails(id: String): Resource<ArtistResponse> {
        return safeApiCall { spotifyApi.getArtistDetails(id) }
    }

}