package com.example.mainactivity.search_module.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.mainactivity.R
import com.example.mainactivity.databinding.ComponentSimpleHeaderBinding
import com.example.mainactivity.search_module.utils.Utils
import com.example.mainactivity.utils.general_utils.Utility

class SearchHeaderComponent : LinearLayout {
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle,
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null, 0)

    private val layoutInflater = LayoutInflater.from(context)
    private val binding = DataBindingUtil.inflate<ComponentSimpleHeaderBinding>(
        layoutInflater,
        R.layout.component_simple_header,
        this,
        true,
    )


    fun settComponent(text: String?) {
        binding.textView.visibility = View.GONE
        if(Utility.isValidText(text)){
            binding.textView.visibility = View.VISIBLE
            binding.textView.text = text
        }

    }

}