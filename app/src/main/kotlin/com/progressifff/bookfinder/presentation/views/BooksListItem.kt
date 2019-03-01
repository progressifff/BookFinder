package com.progressifff.bookfinder.presentation.views

interface BooksListItem {
    fun setBookTitle(title: String)
    fun setBookAuthor(author: String)
    fun loadBookCoverFromUrl(coverUrl: String, bookId: String)
    fun loadBookCoverFromFile(bookId: String)
}