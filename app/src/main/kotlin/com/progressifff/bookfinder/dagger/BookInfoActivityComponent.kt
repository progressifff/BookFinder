package com.progressifff.bookfinder.dagger

import com.progressifff.bookfinder.presentation.BookInfoActivity
import dagger.Subcomponent

@Subcomponent(modules = [BookInfoActivityModule::class])
@BookInfoScope
interface BookInfoActivityComponent {
    fun inject(bookInfoActivity: BookInfoActivity)
}