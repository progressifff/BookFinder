package com.progressifff.bookfinder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.progressifff.bookfinder.presentation.presenters.SearchedBooksListPresenter
import javax.inject.Inject


class SearchedBooksListAdapter @Inject constructor(searchedBooksListPresenter: SearchedBooksListPresenter) : BooksListAdapter<SearchedBooksListPresenter>(searchedBooksListPresenter) {
    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.books_list_item, parent, false)
        return SearchedBookViewHolder(view)
    }

    inner class SearchedBookViewHolder(itemView: View) : BookViewHolder(itemView) {
        init{ itemView.setOnClickListener { booksListPresenter.onBookSelected(adapterPosition) } }
    }
}
