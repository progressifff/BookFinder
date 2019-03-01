package com.progressifff.bookfinder.domain

import com.progressifff.bookfinder.presentation.models.BookInfo
import io.reactivex.Single

interface BookInfoUseCases {
    fun getBookInfo(bookId: String) : Single<BookInfo>
}