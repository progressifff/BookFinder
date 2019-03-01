package com.progressifff.bookfinder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class PaginationAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    abstract fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    abstract fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int)
    
    private var isLoadingViewAdded = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            Constants.LOADING_LIST_VIEW_TYPE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.list_loading_footer_item, parent, false)
                LoadingViewHolder(view)
            }
            else -> onCreateItemViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) != Constants.LOADING_LIST_VIEW_TYPE) onBindItemViewHolder(holder, position)
    }

    override fun getItemViewType(position: Int) = if (position == itemCount -1 && isLoadingViewAdded) Constants.LOADING_LIST_VIEW_TYPE else Constants.LIST_ITEM_VIEW_TYPE

    fun removeLoadingViewFooter() {
        if (isLoadingViewAdded && itemCount > 0) {
            isLoadingViewAdded = false
            notifyItemRemoved(itemCount)
        }
    }

    fun addLoadingViewFooter() {
        if (itemCount > 0) {
            isLoadingViewAdded = true
            notifyItemInserted(itemCount)
        }
    }
}

class LoadingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)