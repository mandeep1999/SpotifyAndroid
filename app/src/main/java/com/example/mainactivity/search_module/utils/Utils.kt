package com.example.mainactivity.search_module.utils

import com.example.mainactivity.search_module.data.constants.SearchResponseConstants
import com.example.mainactivity.search_module.data.models.dtos.SearchItemModel
import com.example.mainactivity.search_module.data.models.dtos.SearchRowComponentModel
import com.example.mainactivity.search_module.data.models.response.ArtistItemFollowersModel
import com.example.mainactivity.utils.general_utils.Utility

object Utils {

    fun isSectionDeveloped(searchRowComponentModel: SearchRowComponentModel): Boolean {
        return when (searchRowComponentModel.type) {
            SearchResponseConstants.ALBUMS, SearchResponseConstants.ARTISTS -> true
            else -> false
        }
    }

    fun formatNumber(value: Long): String {
        val suffixes = arrayOf("", "K", "M", "B", "T")

        var num = value
        var index = 0

        while (num >= 1000) {
            num /= 1000
            index++
        }

        return if (index < suffixes.size) {
            "$num${suffixes[index]}"
        } else {
            "$num${suffixes.last()}"
        }
    }

    fun getMonthlyListeners(followers: ArtistItemFollowersModel?): String {
        return followers?.total?.let {
            "${formatNumber(it.toLong())} Monthly Listeners"
        } ?: ""
    }

    private fun capitalizeEveryWord(sentence: String): String {
        val words = sentence.split(" ")
        val capitalizedWords = words.map { it.capitalize() }
        return capitalizedWords.joinToString(" ")
    }

    fun getCapitalisedList(genres: List<String>?): List<String>? {
        return genres?.map { capitalizeEveryWord(it) }
    }

    fun getHeaderItem(type: String)
            : SearchItemModel.SearchHeaderClass? {
        val text = when(type){
            SearchResponseConstants.ALBUMS -> "Albums"
            SearchResponseConstants.ARTISTS -> "Artists"
            SearchResponseConstants.SHOWS -> "Shows"
            SearchResponseConstants.TRACKS -> "Tracks"
            SearchResponseConstants.EPISODES -> "Episodes"
            SearchResponseConstants.PLAYLISTS -> "Playlists"
            else -> null
        }
        if (Utility.isValidText(text)){
            return SearchItemModel.SearchHeaderClass(titleText = text)
        }
        return null
    }
}