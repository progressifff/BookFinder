package com.progressifff.bookfinder.data

import android.arch.persistence.room.*
import com.google.gson.annotations.SerializedName
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*
import retrofit2.http.Query

interface BooksNetApi {
    @GET("search.json?")
    fun getBooksList(@Query("title")  title: String?,
                     @Query("author") author: String?,
                     @Query("isbn")   isbn: String?,
                     @Query("offset") start:Int,
                     @Query("limit")  limit:Int) : Single<BooksDTO>

    @GET("books/{bookId}.json")
    fun getBookInfo(@Path(value = "bookId", encoded = true)bookId: String?) : Single<BookInfoDTO>
}

@Dao
interface BooksDao {
    @android.arch.persistence.room.Query("SELECT `key`, title, author FROM book LIMIT :count OFFSET :offset")
    fun getBooksList(offset: Int, count: Int): Single<List<BookDTO>>

    @android.arch.persistence.room.Query("SELECT pages_count, publishers FROM book WHERE `key` LIKE :key")
    fun getBookInfo(key: String): Single<BookInfoDTO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBook(book: BookDTO): Long

    @android.arch.persistence.room.Query("DELETE FROM book WHERE `key` LIKE :key")
    fun delete(key: String): Int

    @android.arch.persistence.room.Query("SELECT `index` FROM book WHERE `key` LIKE :key")
    fun getBookIndex(key: String): Long

    @android.arch.persistence.room.Query("SELECT COUNT(*) from book")
    fun booksCount(): Single<Int>

    @android.arch.persistence.room.Query("SELECT (count(*) > 0) from book WHERE `key` LIKE :key")
    fun exists(key: String): Single<Boolean>
}

data class BooksDTO(
    @SerializedName("docs") var docs: List<BookDTO>
)

@Entity(tableName = "book")
data class BookDTO(
    @ColumnInfo(name = "key")                                                   var key: String,
    @Ignore                      @SerializedName("cover_edition_key")     var coverEditionKey: String?,
    @Ignore                      @SerializedName("edition_key")           var editionKey: List<String>?,
    @ColumnInfo(name = "title")  @SerializedName("title_suggest")         var titleSuggest: String?,
    @ColumnInfo(name = "author") @SerializedName("author_name")           var authorName: List<String>?,
    @Embedded                                                                   var bookInfo: BookInfoDTO?
) {
    @PrimaryKey(autoGenerate = true)                                            var index: Int? = null
    constructor() : this("", "", null, "", null, null)
}

data class BookInfoDTO(
    @ColumnInfo(name = "pages_count") @SerializedName("number_of_pages")  val pagesCount: Int?,
    @ColumnInfo(name = "publishers")  @SerializedName("publishers")       val publishers: List<String>?
)