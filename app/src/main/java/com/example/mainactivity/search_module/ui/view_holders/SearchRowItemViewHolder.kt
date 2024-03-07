package com.example.mainactivity.search_module.ui.view_holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.mainactivity.search_module.ui.components.SearchHeaderComponent
import com.example.mainactivity.search_module.ui.components.SearchRowComponent

open class SearchRowItemViewHolder(itemView: View) :  RecyclerView.ViewHolder(itemView){


    class SearchRowComponentViewHolder(v: View) : SearchRowItemViewHolder(v) {
        private var customView: SearchRowComponent? = null

        init {
            customView = v as SearchRowComponent?
        }

        fun getCustomView(): SearchRowComponent? {
            return customView
        }
    }

    class SearchRowHeaderViewHolder(v: View): SearchRowItemViewHolder(v) {
        private var customView: SearchHeaderComponent? = null

        init {
            customView = v as SearchHeaderComponent?
        }

        fun getCustomView(): SearchHeaderComponent? {
            return customView
        }
    }

    class SearchSpaceViewHolder(v:View): SearchRowItemViewHolder(v) {

    }
}