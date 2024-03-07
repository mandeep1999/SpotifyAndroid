package com.example.mainactivity.search_module.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mainactivity.R
import com.example.mainactivity.search_module.data.models.dtos.SearchItemModel
import com.example.mainactivity.search_module.data.models.dtos.SearchRowComponentModel
import com.example.mainactivity.search_module.ui.components.SearchHeaderComponent
import com.example.mainactivity.search_module.ui.components.SearchRowComponent
import com.example.mainactivity.search_module.ui.view_holders.SearchRowItemViewHolder

class SearchAdapter(
    private val context: Context,
    private val onItemClick: (SearchRowComponentModel) -> Unit,
    private val onEndIconClick: (SearchRowComponentModel) -> Unit
) : RecyclerView.Adapter<SearchRowItemViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)


    private var diffCallBack = object : DiffUtil.ItemCallback<SearchItemModel>() {
        override fun areItemsTheSame(oldItem: SearchItemModel, newItem: SearchItemModel): Boolean {
            return when {
                oldItem is SearchRowComponentModel && newItem is SearchRowComponentModel && oldItem.id == newItem.id -> true
                oldItem is SearchItemModel.SearchHeaderClass && newItem is SearchItemModel.SearchHeaderClass && oldItem.titleText == newItem.titleText -> true
                else -> false
            }
        }

        override fun areContentsTheSame(
            oldItem: SearchItemModel,
            newItem: SearchItemModel
        ): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRowItemViewHolder {
        return when(viewType){
            R.layout.component_simple_header -> {
                val itemView = SearchHeaderComponent(parent.context)
                itemView.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                SearchRowItemViewHolder.SearchRowHeaderViewHolder(itemView)
            }
            R.layout.component_search_row -> {
                val itemView = SearchRowComponent(parent.context)
                itemView.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                SearchRowItemViewHolder.SearchRowComponentViewHolder(itemView)
            }

            else -> {
                val view = layoutInflater.inflate(R.layout.component_space, parent, false)
                SearchRowItemViewHolder.SearchSpaceViewHolder(view)
            }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (differ.currentList.get(position)) {
            is SearchItemModel.SearchHeaderClass -> {
                R.layout.component_simple_header
            }
            is SearchRowComponentModel -> {
                R.layout.component_search_row
            }
        }

    }

    override fun onBindViewHolder(holder: SearchRowItemViewHolder, position: Int) {
        val item = differ.currentList[position]
        when(item){
            is SearchRowComponentModel -> {
                (holder as SearchRowItemViewHolder.SearchRowComponentViewHolder).getCustomView()?.setComponent(
                    differ.currentList[position] as SearchRowComponentModel,
                    onItemClick = onItemClick,
                    onEndIconClick = onEndIconClick
                )
            }
            is SearchItemModel.SearchHeaderClass -> {
                (holder as SearchRowItemViewHolder.SearchRowHeaderViewHolder).getCustomView()?.settComponent(
                    item.titleText
                )
            }
        }

    }
}
