package com.progressifff.bookfinder.data

import io.reactivex.Single

class BookInfoLoader(private val booksNetApi: BooksNetApi) : BookInfoRepository{
    override fun getBookInfo(bookId: String): Single<BookInfoDTO> = booksNetApi.getBookInfo(bookId)
}