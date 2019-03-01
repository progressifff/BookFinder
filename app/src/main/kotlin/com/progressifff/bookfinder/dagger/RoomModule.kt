package com.progressifff.bookfinder.dagger

import android.app.Application
import android.arch.persistence.room.Room
import com.progressifff.bookfinder.data.BooksDao
import com.progressifff.bookfinder.data.BooksDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(application: Application) {

    private val booksDatabase = Room.databaseBuilder(application, BooksDataBase::class.java, "BooksDataBase").build()

    @Singleton
    @Provides
    fun providesBooksDatabase(): BooksDataBase = booksDatabase

    @Singleton
    @Provides
    fun providesBooksDao(booksDatabase: BooksDataBase): BooksDao = booksDatabase.booksDao()
}