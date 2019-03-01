package com.progressifff.bookfinder.dagger

import com.progressifff.bookfinder.data.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun providesBooksRepository(booksNetApi: BooksNetApi): BooksRepository = BooksLoader(booksNetApi)

    @Provides
    @Singleton
    fun providesBookInfoRepository(booksNetApi: BooksNetApi): BookInfoRepository = BookInfoLoader(booksNetApi)

    @Provides
    @Singleton
    fun providesBooksDBRepository(booksDao: BooksDao): BooksDBRepository = BooksDBRepository(booksDao)
}