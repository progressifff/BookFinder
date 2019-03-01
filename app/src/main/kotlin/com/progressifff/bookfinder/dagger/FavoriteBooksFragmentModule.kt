package com.progressifff.bookfinder.dagger

import com.progressifff.bookfinder.RxBus
import com.progressifff.bookfinder.domain.FavoriteBooksUseCases
import com.progressifff.bookfinder.presentation.presenters.FavoriteBooksPresenter
import dagger.Module
import dagger.Provides

@Module
class FavoriteBooksFragmentModule {
    @Provides
    @FavoriteBooksScope
    fun providesFavoriteBooksListPresenter(favoriteBooksUseCases: FavoriteBooksUseCases, eventBus: RxBus): FavoriteBooksPresenter = FavoriteBooksPresenter(favoriteBooksUseCases, eventBus)
}