package com.progressifff.bookfinder.presentation

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.progressifff.bookfinder.*
import com.progressifff.bookfinder.presentation.presenters.SearchedBooksListPresenter
import com.progressifff.bookfinder.presentation.views.BooksListView
import java.lang.Exception
import javax.inject.Inject
import com.progressifff.bookfinder.R
import com.progressifff.bookfinder.presentation.models.Book
import android.view.MenuItem


class SearchedBooksListActivity : AppCompatActivity(), BooksListView {

    @Inject
    lateinit var presenter: SearchedBooksListPresenter
    @Inject
    lateinit var booksListAdapter: SearchedBooksListAdapter

    private lateinit var booksListRefresher: SwipeRefreshLayout
    private lateinit var booksList: RecyclerView
    private lateinit var noBooksFoundMsg: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.plusBooksListActivityComponent()
        setContentView(R.layout.activity_books_list)
        initPresenter(savedInstanceState)
        initializeToolbar()
        initializeBooksListView()
        initializeBooksListRefresherView()
        noBooksFoundMsg = findViewById(R.id.booksNotFoundMsg)
    }

    override fun onDestroy() {
        super.onDestroy()
        App.instance.clearBooksListActivityComponent()
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
        if(noBooksFoundMsg.visibility == View.VISIBLE){
            showBooksList()
        }
        booksListAdapter.notifyItemRangeInserted(offset, count)
    }

    override fun resetBooksList() {
        if(noBooksFoundMsg.visibility == View.VISIBLE){
            showBooksList()
        }
        booksListAdapter.notifyDataSetChanged()
    }

    override fun showBookInfo(book: Book) {
        val showBookInfoIntent = Intent(this, BookInfoActivity::class.java)
        showBookInfoIntent.putExtra(Constants.BOOK_KEY, book)
        startActivity(showBookInfoIntent)
    }

    override fun showNoBooksFoundMsg() {
        noBooksFoundMsg.visibility = View.INVISIBLE
        noBooksFoundMsg.visibility = View.VISIBLE
    }

    override fun showLoadBooksListErrorMsg() {
        Toast.makeText(this, R.string.failed_to_load_books, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showBooksList(){
        noBooksFoundMsg.visibility = View.VISIBLE
        noBooksFoundMsg.visibility = View.INVISIBLE
    }

    private fun initializeBooksListView() {
        val linearLayoutManager = LinearLayoutManager(this)
        booksList = findViewById(R.id.searchedBooksList)
        booksList.apply {
            layoutManager = linearLayoutManager
            adapter = booksListAdapter
            addOnScrollListener(presenter.OnScrollListener(linearLayoutManager))
        }
    }

    private fun initializeBooksListRefresherView() {
        booksListRefresher = findViewById(R.id.searchedBooksListRefresher)
        booksListRefresher.setOnRefreshListener { presenter.resetBooksList() }
    }

    private fun initializeToolbar(){
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle(R.string.search_result)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
    }

    private fun initPresenter(savedInstanceState: Bundle?){
        val createPresenter: ()->Unit = {
            App.booksListActivityComponent!!.inject(this)
            presenter.bookTitle = intent.getStringExtra(Constants.TITLE_KEY)
            presenter.bookAuthor = intent.getStringExtra(Constants.AUTHOR_KEY)
            presenter.isbn = intent.getStringExtra(Constants.ISBN_KEY)
            presenter.updateBooksList(true)
        }

        if(savedInstanceState == null){
            createPresenter()
        }
        else{
            try{
                presenter = PresenterManager.restorePresenter(savedInstanceState)
                booksListAdapter = SearchedBooksListAdapter(presenter)
            } catch (e: Exception){
                e.printStackTrace()
                createPresenter()
            }
        }
    }

    override fun showLoadNextPageProgressBar() {
        booksListAdapter.addLoadingViewFooter()
    }

    override fun hideLoadNextPageProgressBar() {
        booksListAdapter.removeLoadingViewFooter()
    }
}