package com.progressifff.bookfinder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.progressifff.bookfinder.presentation.views.BooksListItem
import com.squareup.picasso.Picasso

open class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), BooksListItem {
    protected val bookTitle = itemView.findViewById<TextView>(R.id.bookTitle)!!
    protected val bookAuthor = itemView.findViewById<TextView>(R.id.bookAuthor)!!
    protected val bookCover = itemView.findViewById<ImageView>(R.id.bookCover)!!

    override fun setBookTitle(title: String) {
        bookTitle.text = title
    }

    override fun setBookAuthor(author: String) {
        bookAuthor.text = author
    }

    override fun loadBookCoverFromUrl(coverUrl: String, bookId: String) {
        Picasso.with(itemView.context)
            .load(coverUrl)
            .placeholder(R.drawable.ic_book)
            .error(R.drawable.ic_book)
            .into(bookCover)
    }

    override fun loadBookCoverFromFile(bookId: String) {
        Picasso.with(itemView.context)
            .load(Utils.bookCoverFile(itemView.context, bookId))
            .placeholder(R.drawable.ic_book)
            .error(R.drawable.ic_book)
            .into(bookCover)
    }
}