package com.progressifff.bookfinder.presentation.views

interface FavoriteBooksView : BooksListView {
    fun deleteBook(bookId: String, bookIndex: Int)
}