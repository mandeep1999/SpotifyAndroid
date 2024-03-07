package com.example.mainactivity.search_module.ui.components

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import com.example.mainactivity.R
import com.example.mainactivity.databinding.ComponentSearchRowBinding
import com.example.mainactivity.search_module.data.models.dtos.Icon
import com.example.mainactivity.search_module.data.models.dtos.SearchRowComponentModel
import com.example.mainactivity.search_module.data.models.enums.IconShape
import com.example.mainactivity.utils.general_utils.Utility
import com.example.mainactivity.utils.view_utils.ViewUtility

class SearchRowComponent : ConstraintLayout {

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle,
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null, 0)

    private val layoutInflater = LayoutInflater.from(context)
    private val binding = DataBindingUtil.inflate<ComponentSearchRowBinding>(
        layoutInflater,
        R.layout.component_search_row,
        this,
        true,
    )


    fun setComponent(
        dto: SearchRowComponentModel,
        onItemClick: (SearchRowComponentModel) -> Unit,
        onEndIconClick: (SearchRowComponentModel) -> Unit
    ) {

        with(dto) {
            setComponentTitleTextView(titleText)
            setComponentDescriptionTextView(descriptionText)
            setComponentIconImageView(icon, iconShape)
            setComponentEndIconImageView(endIcon)
            setComponentOnClickListeners(onItemClick, onEndIconClick, this)
        }

    }

    private fun setComponentTitleTextView(titleText: String?) {
        if (Utility.isValidText(titleText)) {
            binding.titleTextView.visibility = VISIBLE
            binding.titleTextView.text = titleText
        } else {
            binding.titleTextView.visibility = GONE
        }
    }

    private fun setComponentDescriptionTextView(descriptionTextView: String?) {
        if (Utility.isValidText(descriptionTextView)) {
            binding.descriptionTextView.visibility = VISIBLE
            binding.descriptionTextView.text = descriptionTextView
        } else {
            binding.descriptionTextView.visibility = GONE
        }
    }

    private fun setComponentIconImageView(icon: Icon?, iconShape: IconShape?) {
        if (hideIconIfIconPropsNull(icon)) {
            return
        }
        if (icon != null) {
            if (iconShape == IconShape.CIRCLE) {
                binding.circleImageView.visibility = VISIBLE
                ViewUtility.loadIconIntoImageView(
                    icon,
                    binding.circleImageView,
                    binding.circleImageView.context
                )
            } else if (iconShape == IconShape.RECTANGLE) {
                binding.imageView.visibility = VISIBLE
                ViewUtility.loadIconIntoImageView(
                    icon,
                    binding.imageView,
                    binding.imageView.context
                )
            }
        } else {
            binding.circleImageView.visibility = GONE
            binding.imageView.visibility = GONE
        }
    }

    private fun hideIconIfIconPropsNull(icon: Icon?): Boolean {
        if (icon == null || (Utility.isValidText(icon.drawable)
                .not() && Utility.isValidText(icon.url).not())
        ) {
            binding.circleImageView.visibility = GONE
            binding.imageView.visibility = GONE
            return true
        }
        return false
    }

    private fun setComponentEndIconImageView(endIcon: Icon?) {
        if (endIcon != null) {
            binding.closeIcon.visibility = VISIBLE
            ViewUtility.loadIconIntoImageView(
                endIcon,
                binding.closeIcon,
                binding.closeIcon.context
            )
        } else {
            binding.closeIcon.visibility = GONE
        }
    }

    private fun setComponentOnClickListeners(
        onClick: (SearchRowComponentModel) -> Unit,
        onEndIconClick: (SearchRowComponentModel) -> Unit,
        searchRowComponentModel: SearchRowComponentModel
    ) {
        binding.root.setOnClickListener {
            onClick.invoke(searchRowComponentModel)
        }

        binding.closeIcon.setOnClickListener {
            onEndIconClick.invoke(searchRowComponentModel)
        }
    }


    companion object {
        const val SUPER_STATE_KEY = "super_state_key"
        const val SPARSE_STATE_KEY = "sparse_state_key"
    }

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>?) {
        dispatchFreezeSelfOnly(container)
    }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>?) {
        dispatchThawSelfOnly(container)
    }

    override fun onSaveInstanceState(): Parcelable? {
        return Bundle().apply {
            putParcelable(SUPER_STATE_KEY, super.onSaveInstanceState())
            putSparseParcelableArray(SPARSE_STATE_KEY, saveChildViewStates())
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var newState = state
        if (newState is Bundle) {
            val childrenState = newState.getSparseParcelableArray<Parcelable>(SPARSE_STATE_KEY)
            childrenState?.let { restoreChildViewStates(it) }
            newState = newState.getParcelable(SUPER_STATE_KEY)
        }
        super.onRestoreInstanceState(newState)
    }

    private fun ViewGroup.saveChildViewStates(): SparseArray<Parcelable> {
        val childViewStates = SparseArray<Parcelable>()
        children.forEach { child -> child.saveHierarchyState(childViewStates) }
        return childViewStates
    }

    private fun ViewGroup.restoreChildViewStates(sparseArray: SparseArray<Parcelable>) {
        children.forEach { child -> child.restoreHierarchyState(sparseArray) }
    }
}