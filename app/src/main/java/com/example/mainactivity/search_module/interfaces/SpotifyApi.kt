package com.example.mainactivity.search_module.interfaces


import com.example.mainactivity.search_module.data.models.response.AlbumResponse
import com.example.mainactivity.search_module.data.models.response.ArtistResponse
import com.example.mainactivity.search_module.data.models.response.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpotifyApi {


    @GET("search/")
    suspend fun getSearchItems(
        @Query("q") searchText: String,
        @Query("type") type: String
    ): Response<SearchResponse>

    @GET("albums/{id}")
    suspend fun getAlbumDetails(@Path("id") id: String): Response<AlbumResponse>

    @GET("artists/{id}")
    suspend fun getArtistDetails(@Path("id") id: String): Response<ArtistResponse>
}