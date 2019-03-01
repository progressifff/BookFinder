package com.progressifff.bookfinder.domain

import com.progressifff.bookfinder.data.BookInfoRepository
import com.progressifff.bookfinder.presentation.models.BookInfo
import io.reactivex.Single

class BookInfoInteractor(private val bookInfoRepository: BookInfoRepository) : BookInfoUseCases{
    override fun getBookInfo(bookId: String): Single<BookInfo> {
        return bookInfoRepository.getBookInfo(bookId)
            .map { serializedBookInfo ->
                return@map BookInfo(if(serializedBookInfo.pagesCount != null) serializedBookInfo.pagesCount.toInt() else 0, ArrayList(serializedBookInfo.publishers!!))
            }
    }
}