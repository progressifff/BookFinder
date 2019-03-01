package com.progressifff.bookfinder.data

import io.reactivex.Single

interface BooksRepository {
    fun getBooksList(
                    title: String?,
                    author: String?,
                    isbn: String?,
                    page:Int,
                    limit:Int) : Single<BooksDTO>
}