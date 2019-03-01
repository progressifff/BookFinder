package com.progressifff.bookfinder.presentation.views

import com.progressifff.bookfinder.presentation.models.Book

interface BooksListView {
    fun showLoadNextPageProgressBar()
    fun hideLoadNextPageProgressBar()
    fun updateBooksListRefresher(hide: Boolean)
    fun insertBooksItemsRange(offset: Int, count: Int)
    fun resetBooksList()
    fun showBookInfo(book: Book)
    fun showNoBooksFoundMsg()
    fun showLoadBooksListErrorMsg()
}