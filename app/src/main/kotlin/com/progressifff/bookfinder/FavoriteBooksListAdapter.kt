package com.progressifff.bookfinder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.progressifff.bookfinder.presentation.presenters.FavoriteBooksPresenter
import com.progressifff.bookfinder.presentation.views.BooksListItem
import com.squareup.picasso.Picasso
import javax.inject.Inject


class FavoriteBooksListAdapter @Inject constructor(favoriteBooksListPresenter: FavoriteBooksPresenter) : BooksListAdapter<FavoriteBooksPresenter>(favoriteBooksListPresenter) {

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_books_list_item, parent, false)
        return FavoriteBookViewHolder(view)
    }

    inner class FavoriteBookViewHolder(itemView: View) : BookViewHolder(itemView) {
        private val deleteBtn = itemView.findViewById<ImageButton>(R.id.deleteBtn)!!
        init{
            itemView.setOnClickListener { booksListPresenter.onBookSelected(adapterPosition) }
            deleteBtn.setOnClickListener { booksListPresenter.onDeleteBookFromFavorites(adapterPosition) }
        }
}
}
