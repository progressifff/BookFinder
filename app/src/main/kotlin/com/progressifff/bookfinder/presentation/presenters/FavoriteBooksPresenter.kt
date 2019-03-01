package com.progressifff.bookfinder.presentation.presenters

import android.annotation.SuppressLint
import com.progressifff.bookfinder.RxBus
import com.progressifff.bookfinder.RxEvent
import com.progressifff.bookfinder.domain.FavoriteBooksUseCases
import com.progressifff.bookfinder.presentation.views.BooksListItem
import com.progressifff.bookfinder.presentation.views.FavoriteBooksView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FavoriteBooksPresenter @Inject constructor(private val favoriteBooksInteractor: FavoriteBooksUseCases, private val eventBus: RxBus) : BooksListPresenter<FavoriteBooksView>(){
    private lateinit var favoriteBooksChangedDisposable: Disposable

    override fun bindView(v: FavoriteBooksView) {
        super.bindView(v)
        favoriteBooksChangedDisposable = eventBus.listen(RxEvent.FavoriteBooksChanged::class.java).subscribe(::handleRxEvent)
    }

    override fun unbindView() {
        super.unbindView()
        favoriteBooksChangedDisposable.dispose()
    }

    @SuppressLint("CheckResult")
    override fun loadBooksList(page: Int) {
        favoriteBooksInteractor.getBooksListBy(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onBooksListReceived, this::onError)
    }

    override fun onBindBooksListItem(booksListItem: BooksListItem, itemPosition: Int){
        val book = booksListState.data[itemPosition]
        booksListItem.setBookTitle(book.title!!)
        booksListItem.setBookAuthor(book.author!!)
        booksListItem.loadBookCoverFromFile(book.openLibraryId!!)
    }

    @SuppressLint("CheckResult")
    fun onDeleteBookFromFavorites(index: Int){
        if(index >= 0 && index < booksListState.data.size){
            val book = booksListState.data.removeAt(index)
            if(booksListState.data.isEmpty()){
                view?.showNoBooksFoundMsg()
            }
            val bookId = book.openLibraryId!!
            favoriteBooksInteractor.delete(bookId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
                view?.deleteBook(bookId, index)
            }
        }
    }

    private fun handleRxEvent(event: RxEvent.FavoriteBooksChanged){
        resetBooksList()
    }
}