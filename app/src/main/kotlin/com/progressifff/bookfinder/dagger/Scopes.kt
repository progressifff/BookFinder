package com.progressifff.bookfinder.dagger

import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class BooksListScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class BookInfoScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FavoriteBooksScope