package com.progressifff.bookfinder.presentation.views

import com.progressifff.bookfinder.presentation.models.Book
import com.progressifff.bookfinder.presentation.models.BookInfo

interface BookInfoView {
    fun update(book: Book, bookInfo: BookInfo)
    fun updateBookInfoRefresher(hide: Boolean)
    fun showLoadBookInfoErrorMsg()
    fun invalidateFavoriteMenuItem()
}