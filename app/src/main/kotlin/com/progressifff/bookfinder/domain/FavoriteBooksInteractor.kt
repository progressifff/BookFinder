package com.progressifff.bookfinder.domain

import com.progressifff.bookfinder.Constants
import com.progressifff.bookfinder.data.BookDTO
import com.progressifff.bookfinder.data.BookInfoDTO
import com.progressifff.bookfinder.data.BooksDBRepository
import com.progressifff.bookfinder.presentation.models.Book
import com.progressifff.bookfinder.presentation.models.BookInfo
import io.reactivex.Completable
import io.reactivex.Single

class FavoriteBooksInteractor(private val booksDBRepository: BooksDBRepository) : FavoriteBooksUseCases {

    override fun getBooksListBy(page: Int): Single<List<Book>> {
        return booksDBRepository.getBooksList(page, Constants.LIMIT_BOOKS_LIST)
            .map { books ->
                val result = arrayListOf<Book>()
                result.ensureCapacity(books.size)
                for(bookDTO in books){
                    result.add(Book.fromBookDTO(bookDTO))
                }
                return@map result
            }
    }

    override fun getBookInfo(key: String): Single<BookInfo> {
        return booksDBRepository.getBookInfo(key).map { bookInfoDTO -> return@map BookInfo.fromBookInfoDTO(bookInfoDTO)}
    }

    override fun add(book: Book, bookInfo: BookInfo): Completable {
        return Completable.fromAction {
            booksDBRepository.addBook(BookDTO(  book.openLibraryId!!,
                book.openLibraryId!!,
                arrayListOf(book.openLibraryId!!),
                book.title,
                arrayListOf(book.author!!),
                BookInfoDTO(bookInfo.pagesCount, bookInfo.publishers)
            ))
        }
    }

    override fun delete(bookKey: String): Completable =  Completable.fromAction { booksDBRepository.delete(bookKey) }

    override fun getBookIndex(bookKey: String): Single<Long> = Single.fromCallable<Long> { booksDBRepository.getBookIndex(bookKey) }

    override fun booksCount(): Single<Int> = booksDBRepository.booksCount()

    override fun exists(bookKey: String): Single<Boolean> = booksDBRepository.exists(bookKey)
}