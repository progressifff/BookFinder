package com.progressifff.bookfinder.data

import io.reactivex.Single

interface BookInfoRepository {
    fun getBookInfo(bookId: String) : Single<BookInfoDTO>
}