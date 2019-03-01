package com.progressifff.bookfinder.dagger

import com.progressifff.bookfinder.domain.BooksListUseCases
import com.progressifff.bookfinder.presentation.presenters.SearchedBooksListPresenter
import dagger.Module
import dagger.Provides

@Module
class BooksListActivityModule {
    @Provides
    @BooksListScope
    fun providesSearchedBooksListPresenter(booksListUseCases: BooksListUseCases): SearchedBooksListPresenter = SearchedBooksListPresenter(booksListUseCases)
}