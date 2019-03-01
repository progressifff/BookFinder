package com.progressifff.bookfinder.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.progressifff.bookfinder.*
import com.progressifff.bookfinder.presentation.models.Book
import com.progressifff.bookfinder.presentation.presenters.FavoriteBooksPresenter
import com.progressifff.bookfinder.presentation.views.FavoriteBooksView
import java.lang.Exception
import javax.inject.Inject

class FavoritesBooksFragment : Fragment(), FavoriteBooksView {

    @Inject
    lateinit var presenter: FavoriteBooksPresenter

    @Inject
    lateinit var booksListAdapter: FavoriteBooksListAdapter

    private lateinit var booksListRefresher: SwipeRefreshLayout
    private lateinit var booksList: RecyclerView
    private lateinit var noFavoriteBooksMsg: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        App.instance.plusFavoriteBooksFragmentComponent()
        val view = inflater.inflate(R.layout.favorites_fragment, container, false)
        initPresenter(savedInstanceState)
        initializeFavoriteBooksListView(view)
        initializeFavoriteBooksListRefresherView(view)
        noFavoriteBooksMsg = view.findViewById(R.id.noFavoritesMsg)
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        App.instance.clearFavoriteBookFragmentComponent()
    }

    override fun onStart() {
        super.onStart()
        presenter.bindView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.unbindView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        PresenterManager.savePresenter(presenter, outState)
    }

    override fun updateBooksListRefresher(hide: Boolean) {
        booksListRefresher.isRefreshing = !hide
    }

    override fun insertBooksItemsRange(offset: Int, count: Int) {
        if(noFavoriteBooksMsg.visibility == View.VISIBLE){
            showBooksList()
        }
        booksListAdapter.notifyItemRangeInserted(offset, count)
    }

    override fun resetBooksList() {
        if(noFavoriteBooksMsg.visibility == View.VISIBLE){
            showBooksList()
        }
        booksListAdapter.notifyDataSetChanged()
    }

    override fun showBookInfo(book: Book) {
        val showBookInfoIntent = Intent(activity, BookInfoActivity::class.java)
        showBookInfoIntent.putExtra(Constants.BOOK_KEY, book)
        startActivity(showBookInfoIntent)
    }

    override fun showNoBooksFoundMsg() {
        noFavoriteBooksMsg.visibility = View.INVISIBLE
        noFavoriteBooksMsg.visibility = View.VISIBLE
    }

    override fun showLoadBooksListErrorMsg() {
        Toast.makeText(App.instance, R.string.failed_to_load_books, Toast.LENGTH_SHORT).show()
    }

    override fun deleteBook(bookId: String, bookIndex: Int) {
        booksListAdapter.notifyItemRemoved(bookIndex)
        Utils.bookCoverFile(App.instance, bookId).delete()
    }

    private fun showBooksList(){
        noFavoriteBooksMsg.visibility = View.VISIBLE
        noFavoriteBooksMsg.visibility = View.INVISIBLE
    }

    override fun showLoadNextPageProgressBar() {
        booksListAdapter.addLoadingViewFooter()
    }

    override fun hideLoadNextPageProgressBar() {
        booksListAdapter.removeLoadingViewFooter()
    }

    private fun initializeFavoriteBooksListView(v: View) {
        val linearLayoutManager = LinearLayoutManager(activity)
        booksList = v.findViewById(R.id.favoriteBooksList)
        booksList.apply {
            layoutManager = linearLayoutManager
            adapter = booksListAdapter
            addOnScrollListener(presenter.OnScrollListener(linearLayoutManager))
        }
    }

    private fun initializeFavoriteBooksListRefresherView(v: View) {
        booksListRefresher = v.findViewById(R.id.favoriteBooksListRefresher)
        booksListRefresher.setOnRefreshListener { presenter.resetBooksList() }
    }

    private fun initPresenter(savedInstanceState: Bundle?){
        val createPresenter: ()->Unit = {
            App.favoriteBooksFragmentComponent!!.inject(this)
            presenter.updateBooksList(true)
        }
        if(savedInstanceState == null){
            createPresenter()
        }
        else{
            try{
                presenter = PresenterManager.restorePresenter(savedInstanceState)
                booksListAdapter = FavoriteBooksListAdapter(presenter)
            } catch (e: Exception){
                e.printStackTrace()
                createPresenter()
            }
        }
    }
}