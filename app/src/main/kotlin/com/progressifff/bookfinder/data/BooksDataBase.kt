package com.progressifff.bookfinder.data

import android.arch.persistence.room.*
import com.google.gson.Gson

@Database(entities = [BookDTO::class], version = 1)
@TypeConverters(Converters::class)
abstract class BooksDataBase : RoomDatabase(){
    abstract fun booksDao(): BooksDao
}

class Converters {
    @TypeConverter
    fun listToJson(value: List<String>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<String>? {
        val objects = Gson().fromJson(value, Array<String>::class.java) as Array<String>
        return objects.toList()
    }
}