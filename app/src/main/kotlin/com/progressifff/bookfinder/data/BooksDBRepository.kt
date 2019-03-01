package com.progressifff.bookfinder.data

import io.reactivex.Single

class BooksDBRepository(private val booksDAO: BooksDao){
    fun getBooksList(page: Int, limit: Int) : Single<List<BookDTO>> = booksDAO.getBooksList(page * limit, limit)
    fun getBookInfo(key: String): Single<BookInfoDTO> = booksDAO.getBookInfo(key)
    fun addBook(book: BookDTO) = booksDAO.addBook(book)
    fun delete(key: String) = booksDAO.delete(key)
    fun getBookIndex(key: String): Long = booksDAO.getBookIndex(key)
    fun booksCount(): Single<Int> = booksDAO.booksCount()
    fun exists(bookKey: String): Single<Boolean> = booksDAO.exists(bookKey)
}
