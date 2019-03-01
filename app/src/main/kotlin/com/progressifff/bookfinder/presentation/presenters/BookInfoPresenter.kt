package com.progressifff.bookfinder.presentation.presenters

import android.annotation.SuppressLint
import com.progressifff.bookfinder.BasePresenter
import com.progressifff.bookfinder.RxBus
import com.progressifff.bookfinder.RxEvent
import com.progressifff.bookfinder.domain.BookInfoUseCases
import com.progressifff.bookfinder.domain.FavoriteBooksUseCases
import com.progressifff.bookfinder.presentation.models.Book
import com.progressifff.bookfinder.presentation.models.BookInfo
import com.progressifff.bookfinder.presentation.views.BookInfoView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BookInfoPresenter @Inject constructor(private val bookInfoInteractor: BookInfoUseCases,
                                            private val favoriteBooksInteractor: FavoriteBooksUseCases,
                                            private val eventBus: RxBus) : BasePresenter<BookInfoView>(){
    var book = Book()
    var bookInfo = BookInfo()
    var isUpdatePending = true
    var isBookFavorite = false
    var isLoaded = false

    override fun bindView(v: BookInfoView){
        super.bindView(v)
        view!!.update(book, bookInfo)
        view!!.invalidateFavoriteMenuItem()
        view!!.updateBookInfoRefresher(!isUpdatePending)
    }

    @SuppressLint("CheckResult")
    fun loadBookInfo(){
        isUpdatePending = true
        favoriteBooksInteractor.exists(book.openLibraryId!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({isFavorite ->
                isBookFavorite = isFavorite
                val getBookInfoTask = if(isFavorite) favoriteBooksInteractor.getBookInfo(book.openLibraryId!!) else bookInfoInteractor.getBookInfo(book.openLibraryId!!)
                getBookInfoTask
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onBookInfoReceived, this::onBookInfoReceiveError)
            }, { })
    }

    @SuppressLint("CheckResult")
    fun onFavoriteMenuItemSelected(checked: Boolean){
        if(checked) favoriteBooksInteractor.add(book, bookInfo).subscribeOn(Schedulers.io()).subscribe()
        else favoriteBooksInteractor.delete(book.openLibraryId!!).subscribeOn(Schedulers.io()).subscribe()
        eventBus.publish(RxEvent.FavoriteBooksChanged())
        isBookFavorite = checked
    }

    private fun onBookInfoReceived(bookInfo: BookInfo){
        this.bookInfo = bookInfo
        view?.update(book, bookInfo)
        isUpdatePending = false
        isLoaded = true
        view?.invalidateFavoriteMenuItem()
        view?.updateBookInfoRefresher(true)
    }

    private fun onBookInfoReceiveError(error: Throwable){
        error.printStackTrace()
        isUpdatePending = false
        isLoaded = false
        view?.invalidateFavoriteMenuItem()
        view?.updateBookInfoRefresher(true)
        view?.showLoadBookInfoErrorMsg()
    }
}