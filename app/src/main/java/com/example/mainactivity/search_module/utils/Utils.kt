package com.example.mainactivity.search_module.utils

import com.example.mainactivity.search_module.data.constants.SearchResponseConstants
import com.example.mainactivity.search_module.data.models.dtos.SearchRowComponentModel
import com.example.mainactivity.search_module.data.models.response.ArtistItemFollowersModel

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
}