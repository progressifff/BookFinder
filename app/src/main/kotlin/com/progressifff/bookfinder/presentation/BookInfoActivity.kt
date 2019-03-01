package com.progressifff.bookfinder.presentation

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.progressifff.bookfinder.*
import com.progressifff.bookfinder.presentation.models.Book
import com.progressifff.bookfinder.presentation.models.BookInfo
import com.progressifff.bookfinder.presentation.presenters.BookInfoPresenter
import com.progressifff.bookfinder.presentation.views.BookInfoView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception
import javax.inject.Inject

class BookInfoActivity : AppCompatActivity(), BookInfoView {

    @Inject
    lateinit var presenter: BookInfoPresenter

    private lateinit var booksListRefresher: SwipeRefreshLayout
    private lateinit var bookTitle: TextView
    private lateinit var bookAuthor: TextView
    private lateinit var bookPublisher: TextView
    private lateinit var bookCover: ImageView
    private lateinit var bookPages: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.plusBookInfoActivityComponent()
        setContentView(R.layout.activity_book_info)
        initPresenter(savedInstanceState)
        initializeToolbar()
        initializeBooksListRefresherView()
        findBookInfoViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        App.instance.clearBookInfoActivityComponent()
    }

    override fun onStart(){
        super.onStart()
        presenter.bindView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.unbindView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.book_info_menu, menu)
        val favoriteMenuItem = menu!!.findItem(R.id.add_to_favorite)
        favoriteMenuItem.isVisible = presenter.isLoaded
        favoriteMenuItem.isChecked = presenter.isBookFavorite
        favoriteMenuItem.icon = if(favoriteMenuItem.isChecked) getDrawable(R.drawable.ic_star_gold) else getDrawable(R.drawable.ic_star)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.add_to_favorite -> {
                item.isChecked = !item.isChecked
                item.icon = if(item.isChecked) getDrawable(R.drawable.ic_star_gold) else getDrawable(R.drawable.ic_star)
                presenter.onFavoriteMenuItemSelected(item.isChecked)
                if(!presenter.isBookFavorite){
                    //Remove cover image
                    Utils.bookCoverFile(this, presenter.book.openLibraryId!!).delete()
                }
                else{
                    Utils.saveImageViewBitmapToFile(bookCover, presenter.book.openLibraryId!!)
                }
            }
            android.R.id.home -> onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        PresenterManager.savePresenter(presenter, outState!!)
    }

    override fun update(book: Book, bookInfo: BookInfo) {
        bookTitle.text = book.title
        bookAuthor.text = book.author
        var publishers = ""
        for(publisher in bookInfo.publishers){
            publishers += "$publisher "
        }
        bookPublisher.text = publishers

        if(bookInfo.pagesCount > 0){
            val pages = "${bookInfo.pagesCount} " + getString(R.string.pages)
            bookPages.text = pages
        }

        //load cover image
        val coverImageFile = Utils.bookCoverFile(this, presenter.book.openLibraryId!!)
        val loadCoverTask = if(coverImageFile.exists()){
            Picasso.with(App.instance).load(coverImageFile)
        }else{
            Picasso.with(App.instance).load(book.largeCoverUrl)
        }
        loadCoverTask
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
            .into(bookCover, object: Callback {
                override fun onSuccess() {
                    if(presenter.isBookFavorite){
                        Utils.saveImageViewBitmapToFile(bookCover, presenter.book.openLibraryId!!)
                    }
                }

                override fun onError() {}
            })
    }

    override fun updateBookInfoRefresher(hide: Boolean) {
        booksListRefresher.isRefreshing = !hide
    }

    override fun showLoadBookInfoErrorMsg() {
        Toast.makeText(this, R.string.failed_to_load_book_info, Toast.LENGTH_SHORT).show()
    }

    override fun invalidateFavoriteMenuItem() {
        invalidateOptionsMenu()
    }

    private fun initPresenter(savedInstanceState: Bundle?){
        val createPresenter: ()->Unit = {
            App.bookInfoActivityComponent!!.inject(this)
            presenter.book = intent.getParcelableExtra(Constants.BOOK_KEY)
            presenter.loadBookInfo()
        }

        if(savedInstanceState == null){
            createPresenter()
        }
        else{
            try{
                presenter = PresenterManager.restorePresenter(savedInstanceState)
            } catch (e: Exception){
                e.printStackTrace()
                createPresenter()
            }
        }
    }

    private fun initializeToolbar(){
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle(R.string.book_info)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
    }

    private fun initializeBooksListRefresherView() {
        booksListRefresher = findViewById(R.id.searchedBooksListRefresher)
        booksListRefresher.setOnRefreshListener { presenter.loadBookInfo() }
    }

    private fun findBookInfoViews(){
        bookTitle =     findViewById(R.id.bookTitle)
        bookAuthor =    findViewById(R.id.bookAuthor)
        bookPublisher = findViewById(R.id.bookPublisher)
        bookCover =     findViewById(R.id.bookCover)
        bookPages =     findViewById(R.id.bookPages)
    }
}