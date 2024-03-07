package com.example.mainactivity.utils.general_utils

import android.util.Log
import com.example.mainactivity.login_module.utils.ObjectGraph
import com.example.mainactivity.search_module.data.models.dtos.SearchRowComponentModel
import java.util.Locale

object Utility {

    fun isValidText(s: String?): Boolean {
        return if (s == null) false else s.isNotEmpty() && s.trim { it <= ' ' }.isNotEmpty()
    }

    fun convertSecondsToHoursAndMinutes(seconds: Long): Pair<Long, Long> {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60

        return Pair(hours, minutes)
    }

    fun convertMillisecondsToFormattedString(milliseconds: Long): String {
        val seconds = milliseconds / 1000
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val remainingSeconds = seconds % 60

        val hoursString = if (hours > 0) "$hours hr" else ""
        val minutesString = if (minutes > 0) "$minutes min" else ""
        val secondsString = if (remainingSeconds > 0) "$remainingSeconds sec" else ""

        val formattedString = listOf(hoursString, minutesString, secondsString)
            .filter { it.isNotBlank() }
            .joinToString(" ")

        return formattedString.ifBlank { "0 sec" }
    }

    fun sortList(tempList: ArrayList<SearchRowComponentModel>): List<SearchRowComponentModel>{
        return tempList.sortedWith(
            compareBy(
                { it.titleText?.firstOrNull()?.isLetter() == false },
                { it.titleText?.lowercase(Locale.ROOT) }
            )
        )
    }

    fun checkIsUserAuthorised(): Boolean {
        return ObjectGraph.getOauth() != null
    }

}