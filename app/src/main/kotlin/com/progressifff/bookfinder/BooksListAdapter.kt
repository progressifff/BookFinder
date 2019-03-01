package com.progressifff.bookfinder

import android.support.v7.widget.RecyclerView
import com.progressifff.bookfinder.presentation.presenters.BooksListPresenter

abstract class BooksListAdapter<T: BooksListPresenter<*>> (protected val booksListPresenter: T) : PaginationAdapter() {

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is BookViewHolder) booksListPresenter.onBindBooksListItem(holder, position)
    }

    override fun getItemCount(): Int {
        return booksListPresenter.booksCount
    }
}
