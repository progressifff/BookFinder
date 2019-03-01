package com.progressifff.bookfinder.dagger

import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, NetModule::class, RoomModule::class, RxBusModule::class, RepositoryModule::class, UseCasesModule::class])
@Singleton
interface AppComponent {
    fun plus(module: BooksListActivityModule): BooksListActivityComponent
    fun plus(module: BookInfoActivityModule): BookInfoActivityComponent
    fun plus(module: FavoriteBooksFragmentModule): FavoriteBooksFragmentComponent
}