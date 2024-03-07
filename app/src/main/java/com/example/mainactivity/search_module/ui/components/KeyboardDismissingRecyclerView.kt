package com.example.mainactivity.search_module.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.RecyclerView

class KeyboardDismissingRecyclerView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyle: Int = -1
) :
    RecyclerView(context!!, attrs, defStyle) {
    private var onKeyboardDismissingScrollListener: OnScrollListener? = null
    var inputMethodManager: InputMethodManager? = null
        /**
         * Returns an [InputMethodManager]
         *
         * @return input method manager
         */
        get() {
            if (null == field) {
                field = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            }
            return field
        }
        private set

    init {
        setOnKeyboardDismissingListener()
    }

    /**
     * Creates [OnScrollListener] that will dismiss keyboard when scrolling if the keyboard
     * has not been dismissed internally before
     */
    private fun setOnKeyboardDismissingListener() {
        onKeyboardDismissingScrollListener = object : OnScrollListener() {
            var isKeyboardDismissedByScroll = false
            override fun onScrollStateChanged(recyclerView: RecyclerView, state: Int) {
                when (state) {
                    SCROLL_STATE_DRAGGING -> if (!isKeyboardDismissedByScroll) {
                        hideKeyboard()
                        isKeyboardDismissedByScroll = !isKeyboardDismissedByScroll
                    }

                    SCROLL_STATE_IDLE -> isKeyboardDismissedByScroll = false
                }
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        addOnScrollListener(onKeyboardDismissingScrollListener!!)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeOnScrollListener(onKeyboardDismissingScrollListener!!)
    }

    /**
     * Hides the keyboard
     */
    fun hideKeyboard() {
        inputMethodManager?.hideSoftInputFromWindow(windowToken, 0)
        clearFocus()
    }
}