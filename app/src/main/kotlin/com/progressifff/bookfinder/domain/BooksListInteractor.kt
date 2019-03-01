package com.progressifff.bookfinder.domain

import com.progressifff.bookfinder.Constants.LIMIT_BOOKS_LIST
import com.progressifff.bookfinder.data.BooksRepository
import com.progressifff.bookfinder.presentation.models.Book
import io.reactivex.Single


class BooksListInteractor(private val booksRepository: BooksRepository) : BooksListUseCases{

    override fun getBooksListBy(title: String?,
                                author: String?,
                                isbn: String?,
                                page: Int): Single<List<Book>> {

        return booksRepository.getBooksList(title, author, isbn, page, LIMIT_BOOKS_LIST)
            .map { serializedBooks ->
                val result = arrayListOf<Book>()
                result.ensureCapacity(serializedBooks.docs.size)
                for(bookDTO in serializedBooks.docs){
                    bookDTO.key = bookDTO.coverEditionKey ?: bookDTO.editionKey!![0]
                    result.add(Book.fromBookDTO(bookDTO))
                }
                return@map result
            }
    }


}