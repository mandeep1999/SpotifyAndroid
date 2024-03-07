package com.example.mainactivity.search_module.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mainactivity.search_module.data.models.dtos.SearchRowComponentModel
import com.example.mainactivity.search_module.ui.components.SearchRowComponent

class SearchAdapter(
    private val onItemClick: (SearchRowComponentModel) -> Unit,
    private val onEndIconClick: (SearchRowComponentModel) -> Unit
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var customView: SearchRowComponent? = null

        init {
            customView = v as SearchRowComponent?
        }

        fun getCustomView(): SearchRowComponent? {
            return customView
        }
    }

    private var diffCallBack = object : DiffUtil.ItemCallback<SearchRowComponentModel>() {
        override fun areItemsTheSame(
            oldItem: SearchRowComponentModel,
            newItem: SearchRowComponentModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SearchRowComponentModel,
            newItem: SearchRowComponentModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = SearchRowComponent(parent.context)
        itemView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return  differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.getCustomView()?.setComponent(
            differ.currentList[position],
            onItemClick = onItemClick,
            onEndIconClick = onEndIconClick
        )
    }
}
