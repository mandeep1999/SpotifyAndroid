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

/**
 * Developed by Mandeep Singh on 07-03-2024
 * @param context -> Context, Used to get Layout inflater.
 * @param onItemClick -> The callback to be invoked when an compatible item from the list is selected.
 * @param onEndIconClick -> The callback to be invoked, when search row component end icon is clicked
 */
class SearchAdapter(
    private val context: Context,
    private val onItemClick: (SearchRowComponentModel) -> Unit,
    private val onEndIconClick: (SearchRowComponentModel) -> Unit
) : RecyclerView.Adapter<SearchRowItemViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)


    /**
     * The difference logic, whether to update items of the list or not.
     * Since this adapter supports multiple view holders, have to first check the
     * data model type and compare their identifier.
     */
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

    /**
     * Function to get correct view holder for the recycler view.
     * @param parent -> The parent View Group
     * @param viewType -> This decides the type of view holder we need to create
     */
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

    /**
     * This os responsible for telling the type of view needs to be inflated,
     * on the basis of data model of the item.
     * @param position -> The position is used to get the item from the list.
     */
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

    /**
     * This function is used to set the properties from the data model to the view holder.
     */
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
