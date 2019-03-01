package com.progressifff.bookfinder

import android.app.Application
import com.progressifff.bookfinder.dagger.*

class App : Application(){
    companion object {
        private lateinit var INSTANCE: App
        val instance: App get() = INSTANCE
        lateinit var appComponent: AppComponent
        var booksListActivityComponent: BooksListActivityComponent? = null
        var bookInfoActivityComponent: BookInfoActivityComponent? = null
        var favoriteBooksFragmentComponent: FavoriteBooksFragmentComponent? = null
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        //appComponent =  DaggerAppComponent.create()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .netModule(NetModule())
            .rxBusModule(RxBusModule())
            .roomModule(RoomModule(this))
            .repositoryModule(RepositoryModule())
            .useCasesModule(UseCasesModule())
            .build()
    }

    fun plusBooksListActivityComponent(): BooksListActivityComponent{
        if (booksListActivityComponent == null) {
            booksListActivityComponent = appComponent.plus(BooksListActivityModule())
        }
        return booksListActivityComponent!!
    }

    fun clearBooksListActivityComponent() {
        booksListActivityComponent = null
    }

    fun plusBookInfoActivityComponent(): BookInfoActivityComponent{
        if (bookInfoActivityComponent == null) {
            bookInfoActivityComponent = appComponent.plus(BookInfoActivityModule())
        }
        return bookInfoActivityComponent!!
    }

    fun clearBookInfoActivityComponent() {
        bookInfoActivityComponent = null
    }

    fun plusFavoriteBooksFragmentComponent(): FavoriteBooksFragmentComponent{
        if (favoriteBooksFragmentComponent == null) {
            favoriteBooksFragmentComponent = appComponent.plus(FavoriteBooksFragmentModule())
        }
        return favoriteBooksFragmentComponent!!
    }

    fun clearFavoriteBookFragmentComponent() {
        favoriteBooksFragmentComponent = null
    }
}