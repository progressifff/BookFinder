package com.progressifff.bookfinder.dagger

import com.progressifff.bookfinder.RxBus
import com.progressifff.bookfinder.RxEvent
import com.progressifff.bookfinder.domain.BookInfoUseCases
import com.progressifff.bookfinder.domain.FavoriteBooksUseCases
import com.progressifff.bookfinder.presentation.presenters.BookInfoPresenter
import dagger.Module
import dagger.Provides

@Module
class BookInfoActivityModule {
    @Provides
    @BookInfoScope
    fun providesBookInfoPresenter(booksInfoUseCases: BookInfoUseCases, favoriteBooksUseCases: FavoriteBooksUseCases, eventBus: RxBus): BookInfoPresenter = BookInfoPresenter(booksInfoUseCases, favoriteBooksUseCases, eventBus)
}