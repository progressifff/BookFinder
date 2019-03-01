package com.progressifff.bookfinder

import com.progressifff.bookfinder.presentation.models.Book

sealed class BooksListState {
    abstract val pageNum: Int
    abstract val allItemsLoaded: Boolean
    abstract val data: ArrayList<Book>
}

data class DefaultBooksListState(override val pageNum: Int = 0, override val allItemsLoaded: Boolean = false, override val data: ArrayList<Book> = arrayListOf()) : BooksListState()
data class LoadingBooksListState(override val pageNum: Int = 0, override val allItemsLoaded: Boolean = false, override val data: ArrayList<Book> = arrayListOf()) : BooksListState()
data class PaginatingBooksListState(override val pageNum: Int = 0, override val allItemsLoaded: Boolean = false, override val data: ArrayList<Book> = arrayListOf()) : BooksListState()
data class ErrorBooksListState(val errorMessage: String, override val pageNum: Int, override val allItemsLoaded: Boolean, override val data: ArrayList<Book>) : BooksListState()
