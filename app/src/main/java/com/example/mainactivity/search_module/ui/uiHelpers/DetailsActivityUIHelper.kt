package com.example.mainactivity.search_module.ui.uiHelpers

import androidx.fragment.app.Fragment
import com.example.mainactivity.search_module.data.constants.SearchResponseConstants
import com.example.mainactivity.search_module.ui.fragments.AlbumFragment
import com.example.mainactivity.search_module.ui.fragments.ArtistFragment

class DetailsActivityUIHelper {


    fun getDetailFragment(id: String, type: String): Fragment? {
        return when (type) {
            SearchResponseConstants.ALBUMS -> {
                AlbumFragment.newInstance(id, type)
            }

            SearchResponseConstants.ARTISTS -> {
                ArtistFragment.newInstance(id, type)
            }

            else -> {
                null
            }
        }
    }
}