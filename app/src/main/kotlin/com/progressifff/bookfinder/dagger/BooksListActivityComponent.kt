package com.progressifff.bookfinder.dagger

import com.progressifff.bookfinder.presentation.SearchedBooksListActivity
import dagger.Subcomponent

@Subcomponent(modules = [BooksListActivityModule::class])
@BooksListScope
interface BooksListActivityComponent {
    fun inject(searchedBooksListActivity: SearchedBooksListActivity)
}