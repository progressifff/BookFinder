package com.progressifff.bookfinder.domain

import com.progressifff.bookfinder.presentation.models.Book
import com.progressifff.bookfinder.presentation.models.BookInfo
import io.reactivex.Completable
import io.reactivex.Single

interface FavoriteBooksUseCases {
    fun getBooksListBy(page: Int) : Single<List<Book>>
    fun getBookInfo(key: String): Single<BookInfo>
    fun add(book: Book, bookInfo: BookInfo): Completable
    fun delete(bookKey: String): Completable
    fun getBookIndex(bookKey: String): Single<Long>
    fun booksCount(): Single<Int>
    fun exists(bookKey: String): Single<Boolean>
}