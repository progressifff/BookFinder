package com.progressifff.bookfinder.presentation.presenters

import android.annotation.SuppressLint
import com.progressifff.bookfinder.domain.BooksListUseCases
import com.progressifff.bookfinder.presentation.views.BooksListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

open class SearchedBooksListPresenter @Inject constructor(private val booksListInteractor: BooksListUseCases) : BooksListPresenter<BooksListView>(){
    var bookTitle: String = ""
    var bookAuthor: String = ""
    var isbn: String = ""

    @SuppressLint("CheckResult")
    override fun loadBooksList(page:Int) {
        val title = if(bookTitle == "") null else bookTitle
        val author = if(bookAuthor == "") null else bookAuthor
        val _isbn = if(isbn == "") null else isbn

        booksListInteractor.getBooksListBy(title, author, _isbn, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onBooksListReceived, this::onError)
    }
}
