package com.example.mainactivity.search_module.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mainactivity.R
import com.example.mainactivity.databinding.SimpleItemRowBinding

class SimpleAdapter : RecyclerView.Adapter<SimpleAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        fun bindView(item: String, holder: ViewHolder) {
            val textView = holder.itemView.findViewById<TextView>(R.id.text_view)
            textView?.let {
                it.text = item
            }
        }
    }

    private var diffCallBack = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<SimpleItemRowBinding>(
            inflater,
            R.layout.simple_item_row,
            parent,
            false
        )
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(differ.currentList[position], holder)
    }
}