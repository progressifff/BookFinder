package com.progressifff.bookfinder.presentation.presenters

import android.support.v7.widget.LinearLayoutManager
import com.progressifff.bookfinder.*
import com.progressifff.bookfinder.Constants.LIMIT_BOOKS_LIST
import com.progressifff.bookfinder.presentation.models.Book
import com.progressifff.bookfinder.presentation.views.BooksListItem
import com.progressifff.bookfinder.presentation.views.BooksListView
import kotlin.properties.Delegates

abstract class BooksListPresenter<V: BooksListView> : BasePresenter<V>() {
    protected var booksListState by Delegates.observable<BooksListState>(DefaultBooksListState()) { _, _, _ -> onBooksListStateUpdated() }
    val booksCount: Int get() = booksListState.data.size
    private var previousBooksCount = booksCount
    private var isUpdatePending: Boolean = false

    override fun bindView(v: V) {
        super.bindView(v)

        if(isUpdatePending){
            processNewBooksListState()
            isUpdatePending = false
        }
    }

    open fun onBindBooksListItem(booksListItem: BooksListItem, itemPosition: Int){
        val book = booksListState.data[itemPosition]
        booksListItem.setBookTitle(book.title!!)
        booksListItem.setBookAuthor(book.author!!)
        booksListItem.loadBookCoverFromUrl(book.coverUrl, book.openLibraryId!!)
    }

    fun resetBooksList(){
        updateBooksList(true)
    }

    fun updateBooksList(reset: Boolean = false){
        if (reset) {
            booksListState = LoadingBooksListState(booksListState.pageNum, false, booksListState.data)
            loadBooksList()
        }
        else {
            booksListState = PaginatingBooksListState(booksListState.pageNum, false, booksListState.data)
            loadBooksList(booksListState.pageNum)
        }
    }

    fun onBookSelected(index: Int){
        view!!.showBookInfo(booksListState.data[index])
    }

    private fun onBooksListStateUpdated() {
        if(view != null){
            processNewBooksListState()
        }
        else isUpdatePending = true
    }

    private fun processNewBooksListState(){
        when (booksListState) {
            is LoadingBooksListState -> view!!.updateBooksListRefresher(false)
            is DefaultBooksListState -> {
                view!!.updateBooksListRefresher(true)
                if(booksListState.data.isEmpty()){
                    view!!.showNoBooksFoundMsg()
                }
                else {
                    view!!.insertBooksItemsRange(previousBooksCount, booksListState.data.size)
                }
            }
            is ErrorBooksListState -> {
                view!!.updateBooksListRefresher(true)
                view!!.hideLoadNextPageProgressBar()
                view!!.showLoadBooksListErrorMsg()
            }
        }
    }

    private fun loadNextBooksPage(){
        view!!.showLoadNextPageProgressBar()
        updateBooksList()
    }

    protected fun onBooksListReceived(booksList: List<Book>) {
        val currentPageNum: Int
        val currentBooksList: ArrayList<Book>

        if(booksListState is LoadingBooksListState){
            currentPageNum = 1
            previousBooksCount = 0
            currentBooksList = arrayListOf()
            view?.resetBooksList()
        }
        else{
            currentPageNum = booksListState.pageNum + 1
            previousBooksCount = booksListState.data.size
            currentBooksList = booksListState.data
        }

        currentBooksList.addAll(booksList)
        booksListState = DefaultBooksListState(currentPageNum, booksList.size < LIMIT_BOOKS_LIST, currentBooksList)
    }

    protected fun onError(error: Throwable) {
        error.printStackTrace()
        booksListState = ErrorBooksListState(error.message ?: "", booksListState.pageNum, booksListState.allItemsLoaded, booksListState.data)
    }

    protected abstract fun loadBooksList(page:Int = 0)

    inner class OnScrollListener(layoutManager: LinearLayoutManager) : PaginationScrollListener(layoutManager) {
        override fun isLoading() = (booksListState is LoadingBooksListState) || (booksListState is PaginatingBooksListState)
        override fun loadMoreItems() = loadNextBooksPage()
        override fun getTotalPageCount() = LIMIT_BOOKS_LIST
        override fun isLastPage() = booksListState.allItemsLoaded
    }
}