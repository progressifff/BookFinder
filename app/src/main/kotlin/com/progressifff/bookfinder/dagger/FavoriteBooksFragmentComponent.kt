package com.progressifff.bookfinder.dagger

import com.progressifff.bookfinder.presentation.FavoritesBooksFragment
import dagger.Subcomponent

@Subcomponent(modules = [FavoriteBooksFragmentModule::class])
@FavoriteBooksScope
interface FavoriteBooksFragmentComponent {
    fun inject(favoriteBooksBooksFragment: FavoritesBooksFragment)
}