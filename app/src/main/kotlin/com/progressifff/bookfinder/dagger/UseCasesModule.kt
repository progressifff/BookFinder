package com.progressifff.bookfinder.dagger

import com.progressifff.bookfinder.data.BookInfoRepository
import com.progressifff.bookfinder.data.BooksDBRepository
import com.progressifff.bookfinder.data.BooksRepository
import com.progressifff.bookfinder.domain.*
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {
    @Provides
    fun providesBooksListUseCases(booksRepository: BooksRepository): BooksListUseCases = BooksListInteractor(booksRepository)

    @Provides
    fun providesBookInfoUseCases(bookInfoRepository: BookInfoRepository): BookInfoUseCases = BookInfoInteractor(bookInfoRepository)

    @Provides
    fun providesFavoriteBooksCases(booksDbRepository: BooksDBRepository): FavoriteBooksUseCases = FavoriteBooksInteractor(booksDbRepository)
}