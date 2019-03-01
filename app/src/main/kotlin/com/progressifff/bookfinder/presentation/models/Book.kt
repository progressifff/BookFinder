package com.progressifff.bookfinder.presentation.models

import android.os.Parcel
import android.os.Parcelable
import com.progressifff.bookfinder.data.BookDTO

class Book(var openLibraryId: String? = "",
           var author: String? = "",
           var title: String? = "") : Parcelable{

    val coverUrl: String get() = "http://covers.openlibrary.org/b/olid/$openLibraryId-M.jpg?default=false"
    val largeCoverUrl: String get() = "http://covers.openlibrary.org/b/olid/$openLibraryId-L.jpg?default=false"

    constructor(parcel: Parcel) : this() {
        openLibraryId = parcel.readString()!!
        author = parcel.readString()!!
        title = parcel.readString()!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(openLibraryId)
        parcel.writeString(author)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }

        fun fromBookDTO(bookDTO: BookDTO): Book = Book(bookDTO.key, bookDTO.titleSuggest, bookDTO.authorName!![0])
    }
}