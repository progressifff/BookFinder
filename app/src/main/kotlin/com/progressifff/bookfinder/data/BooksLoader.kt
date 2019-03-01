package com.progressifff.bookfinder.data

import io.reactivex.Single

class BooksLoader(private val booksNetApi: BooksNetApi) : BooksRepository{
    override fun getBooksList(
                                title: String?,
                                author: String?,
                                isbn: String?,
                                page: Int,
                                limit: Int): Single<BooksDTO> = booksNetApi.getBooksList(title, author, isbn,page * limit, limit)
}