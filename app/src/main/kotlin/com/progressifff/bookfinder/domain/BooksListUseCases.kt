package com.progressifff.bookfinder.domain

import com.progressifff.bookfinder.presentation.models.Book
import io.reactivex.Single

interface BooksListUseCases {
    fun getBooksListBy(
                        title: String?,
                        author: String?,
                        isbn: String?,
                        page: Int) : Single<List<Book>>
}