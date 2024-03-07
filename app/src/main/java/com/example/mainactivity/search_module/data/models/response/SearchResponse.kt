package com.example.mainactivity.search_module.data.models.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(


    @SerializedName("albums")
    val albums: AlbumsModel? = null,

    @SerializedName("artists")
    val artists: ArtistsModel? = null,

    @SerializedName("tracks")
    val tracks: TracksModel? = null,

    @SerializedName("playlists")
    val playLists: PlayListsModel? = null,

    @SerializedName("shows")
    val shows: ShowsModel? = null,

    @SerializedName("episodes")
    val episodes: EpisodesModel? = null
)
