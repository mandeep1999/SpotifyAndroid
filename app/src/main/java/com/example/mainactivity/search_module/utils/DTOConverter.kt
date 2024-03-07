package com.example.mainactivity.search_module.utils

import com.example.mainactivity.search_module.data.constants.SearchResponseConstants
import com.example.mainactivity.search_module.data.models.dtos.Icon
import com.example.mainactivity.search_module.data.models.dtos.SearchRowComponentModel
import com.example.mainactivity.search_module.data.models.enums.IconShape
import com.example.mainactivity.search_module.data.models.response.AlbumItemModel
import com.example.mainactivity.search_module.data.models.response.AlbumsModel
import com.example.mainactivity.search_module.data.models.response.ArtistsModel
import com.example.mainactivity.search_module.data.models.response.EpisodeItemModel
import com.example.mainactivity.search_module.data.models.response.EpisodesModel
import com.example.mainactivity.search_module.data.models.response.ItemImage
import com.example.mainactivity.search_module.data.models.response.PlayListsModel
import com.example.mainactivity.search_module.data.models.response.SearchResponse
import com.example.mainactivity.search_module.data.models.response.ShowItemModel
import com.example.mainactivity.search_module.data.models.response.ShowsModel
import com.example.mainactivity.search_module.data.models.response.TrackItemModel
import com.example.mainactivity.search_module.data.models.response.TracksModel
import com.example.mainactivity.utils.general_utils.Utility
import okhttp3.internal.toImmutableList

object DTOConverter {

    fun getSearchItemList(data: SearchResponse?): List<SearchRowComponentModel> {
        val tempList = ArrayList<SearchRowComponentModel>()
        data?.let {
            val albumsList = getListFromAlbums(it.albums)
            val artistsList = getListFromArtists(it.artists)
            val tracksList = getListFromTracks(it.tracks)
            val playListsList = getListFromPlayLists(it.playLists)
            val showsList = getListFromShows(it.shows)
            val episodesList = getListFromEpisodes(it.episodes)
            tempList.addAll(albumsList)
            tempList.addAll(artistsList)
            tempList.addAll(tracksList)
            tempList.addAll(playListsList)
            tempList.addAll(showsList)
            tempList.addAll(episodesList)
        }
        return Utility.sortList(tempList)
    }

    private fun getListFromAlbums(albums: AlbumsModel?): List<SearchRowComponentModel> {
        return albums?.items?.map {
            SearchRowComponentModel(
                id = it.id ?: "",
                titleText = it.name,
                descriptionText = getAlbumDescriptionText(it),
                icon = Icon(drawable = null, url = getSmallerImage(it.images)),
                iconShape = IconShape.RECTANGLE,
                endIcon = Icon(drawable = "baseline_chevron_right_24"),
                type = SearchResponseConstants.ALBUMS
            )
        }?.toImmutableList() ?: emptyList()
    }

    private fun getListFromArtists(artists: ArtistsModel?): List<SearchRowComponentModel> {
        return artists?.items?.map {
            SearchRowComponentModel(
                id = it.id ?: "",
                titleText = it.name,
                descriptionText = getArtistsDescriptionText(),
                icon = Icon(drawable = null, url = getSmallerImage(it.images)),
                iconShape = IconShape.CIRCLE,
                endIcon = Icon(drawable = "baseline_chevron_right_24"),
                type = SearchResponseConstants.ARTISTS
            )
        }?.toMutableList() ?: emptyList()
    }

    fun getListFromTracks(tracks: TracksModel?): List<SearchRowComponentModel> {
        return tracks?.items?.map {
            SearchRowComponentModel(
                id = it.id ?: "",
                titleText = it.name,
                descriptionText = getTracksDescriptionText(it),
                icon = Icon(drawable = null, url = getSmallerImage(it.album?.images)),
                iconShape = IconShape.RECTANGLE,
                endIcon = Icon(drawable = "baseline_chevron_right_24"),
                type = SearchResponseConstants.TRACKS
            )
        }?.toMutableList() ?: emptyList()
    }

    private fun getListFromPlayLists(playLists: PlayListsModel?): List<SearchRowComponentModel> {
        return playLists?.items?.map {
            SearchRowComponentModel(
                id = it.id ?: "",
                titleText = it.name,
                icon = Icon(drawable = null, url = getSmallerImage(it.image)),
                descriptionText = getPlayListDescriptionText(),
                iconShape = IconShape.RECTANGLE,
                endIcon = Icon(drawable = "baseline_chevron_right_24"),
                type = SearchResponseConstants.PLAYLISTS
            )
        }?.toMutableList() ?: emptyList()
    }

    private fun getListFromShows(shows: ShowsModel?): List<SearchRowComponentModel> {
        return shows?.items?.map {
            SearchRowComponentModel(
                id = it.id ?: "",
                titleText = it.name,
                descriptionText = getShowsDescriptionText(it),
                icon = Icon(drawable = null, url = getSmallerImage(it.images)),
                iconShape = IconShape.RECTANGLE,
                endIcon = Icon(drawable = "baseline_chevron_right_24"),
                type = SearchResponseConstants.SHOWS
            )
        }?.toMutableList() ?: emptyList()
    }

    private fun getListFromEpisodes(episodes: EpisodesModel?): List<SearchRowComponentModel> {
        return episodes?.items?.map {
            SearchRowComponentModel(
                id = it.id ?: "",
                titleText = it.name,
                descriptionText = getEpisodesDescriptionText(it),
                icon = Icon(drawable = null, url = getSmallerImage(it.images)),
                iconShape = IconShape.RECTANGLE,
                endIcon = Icon(drawable = "baseline_chevron_right_24"),
                type = SearchResponseConstants.EPISODES
            )
        }?.toMutableList() ?: emptyList()
    }

    private fun getAlbumDescriptionText(albumItemModel: AlbumItemModel): String {
        val artistString = albumItemModel.artists?.map { it.name }?.joinToString(", ")
        return "Album: $artistString"
    }

    private fun getArtistsDescriptionText(): String {
        return "Artist"
    }

    private fun getTracksDescriptionText(trackItemModel: TrackItemModel): String {
        val artistString = trackItemModel.artists?.map { it.name }?.joinToString(", ")
        return "Song: $artistString"
    }

    private fun getPlayListDescriptionText(): String {
        return "Playlist"
    }

    private fun getShowsDescriptionText(showItemModel: ShowItemModel): String {
        return "Podcast ${showItemModel.publisher}"
    }

    private fun getEpisodesDescriptionText(episodeItemModel: EpisodeItemModel): String {
        val durationString =
            episodeItemModel.durationMS?.let { Utility.convertMillisecondsToFormattedString(it) }
                ?: ""
        return "Episode: $durationString"
    }

    private fun getSmallerImage(images: List<ItemImage>?): String? {
        images?.let {
            return it.getOrNull(2)?.url ?: it.getOrNull(1)?.url ?: it.getOrNull(0)?.url
        }
        return null
    }

    private fun getBiggerImage(images: List<ItemImage>?): String? {
        images?.let {
            return it.getOrNull(0)?.url ?: it.getOrNull(1)?.url ?: it.getOrNull(2)?.url
        }
        return null
    }

    fun getIconFromImage(images: List<ItemImage>?): Icon {
        val biggerImage = getBiggerImage(images)
        return Icon("", biggerImage)
    }
}