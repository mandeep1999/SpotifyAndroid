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

/**
 * Developed by Mandeep Singh on 07-03-2024. This is a custom component to be directly used in layouts
 * or inflated dynamically.
 */
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


    /**
     * Function to be called from the parent, to set the properties of this custom component.
     * @param dto -> The Input model to set the fields like a title, or a description
     * @param onItemClick -> Callback to be called when the item is clicked.
     * @param onEndIconClick -> Callback to be invoked when clicking on the end icon of custom component.
     * @return void
     */
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

    /**
     * Function to set up the title textview of the custom component. It also controls the
     * visibility of that text view
     * @param titleText -> Input text to be set on the textview.
     */
    private fun setComponentTitleTextView(titleText: String?) {
        if (Utility.isValidText(titleText)) {
            binding.titleTextView.visibility = VISIBLE
            binding.titleTextView.text = titleText
        } else {
            binding.titleTextView.visibility = GONE
        }
    }

    /**
     * Function to set up the description textview of the custom component. It also controls the\
     * visibility of textview.
     * @param descriptionText -> The text to be set on the description text view.
     */
    private fun setComponentDescriptionTextView(descriptionText: String?) {
        if (Utility.isValidText(descriptionText)) {
            binding.descriptionTextView.visibility = VISIBLE
            binding.descriptionTextView.text = descriptionText
        } else {
            binding.descriptionTextView.visibility = GONE
        }
    }

    /**
     * Function to load an image on the first image view of the component
     * @param icon -> The ICON model which contains a drawable name or a url for image
     * @param iconShape -> The Shape of the image, can be rectangular or circular
     */
    private fun setComponentIconImageView(icon: Icon?, iconShape: IconShape?) {
        if (hideIconIfIconPropsNull(icon)) {
            return
        }
        binding.circleImageView.visibility = GONE
        binding.imageView.visibility = GONE


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
        }
    }

    /**
     * Function to hide the image view, if both drawable and url props of the Icon model is invalid.
     * @param icon -> The icon model to check for props
     */
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

    /**
     * Function to set the icon in the image view at the end of custom view, if an
     * icon is not provided, it just hides the image view from the layout.
     * @param endIcon -> The Icon model to be used for drawable or url.
     */
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

    /**
     * Function to set the on click listeners of the custom component.
     * @param onClick -> Callback for complete component click.
     * @param onEndIconClick -> Callback for on click or icon at the end
     * @param searchRowComponentModel ->  the complete dto, to identify the item clicked.
     */
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

    /**
     * Below code is responsible for maintaining the state of this custom component.
     */
    companion object {
        const val SUPER_STATE_KEY = "super_state_key"
        const val SPARSE_STATE_KEY = "sparse_state_key"
    }

    /**
     * Override it to just save the state of the parent container.
     */
    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>?) {
        dispatchFreezeSelfOnly(container)
    }

    /**
     * Override it to to just restore the state of the parent container.
     */
    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>?) {
        dispatchThawSelfOnly(container)
    }

    /**
     * Function to save the state of the parent, as well as all the child view states.
     * The children states are save using a sparse array.
     */
    override fun onSaveInstanceState(): Parcelable? {
        return Bundle().apply {
            putParcelable(SUPER_STATE_KEY, super.onSaveInstanceState())
            putSparseParcelableArray(SPARSE_STATE_KEY, saveChildViewStates())
        }
    }

    /**
     * Function to restore the state of the parent container. as well as all the child view states.
     */
    override fun onRestoreInstanceState(state: Parcelable?) {
        var newState = state
        if (newState is Bundle) {
            val childrenState = newState.getSparseParcelableArray<Parcelable>(SPARSE_STATE_KEY)
            childrenState?.let { restoreChildViewStates(it) }
            newState = newState.getParcelable(SUPER_STATE_KEY)
        }
        super.onRestoreInstanceState(newState)
    }

    /**
     * Extension function to save the state of all children of the parent container
     * in a sparse array.
     * @return SparseArray<Parcelable> -> containing the children states.
     */
    private fun ViewGroup.saveChildViewStates(): SparseArray<Parcelable> {
        val childViewStates = SparseArray<Parcelable>()
        children.forEach { child -> child.saveHierarchyState(childViewStates) }
        return childViewStates
    }

    /**
     * Extension function to restore the state of children of the parent container
     * from the sparse array.
     * @param sparseArray -> Sparse Array containing the states.
     */
    private fun ViewGroup.restoreChildViewStates(sparseArray: SparseArray<Parcelable>) {
        children.forEach { child -> child.restoreHierarchyState(sparseArray) }
    }
}