package com.progressifff.bookfinder

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import com.progressifff.bookfinder.presentation.models.Book
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream

object Utils {
    fun bookCoverFile(context: Context, bookId: String): File{
        return File("${context.filesDir}/${Constants.BOOK_LARGE_IMAGES_DIR}/$bookId.png")
    }

    fun saveImageViewBitmapToFile(imageView: ImageView, fileName: String){
        if (imageView.drawable is BitmapDrawable) {
            //Save book image
            val bitmapDrawable = imageView.drawable as BitmapDrawable
            if (bitmapDrawable.bitmap != null) {
                val file = Utils.bookCoverFile(imageView.context, fileName)
                Completable.fromAction {
                    file.parentFile.mkdirs()
                    file.createNewFile()
                    FileOutputStream(file).use { fOut ->
                        bitmapDrawable.bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut)
                        fOut.flush()
                    }
                }.subscribeOn(Schedulers.io()).subscribe()
            }
        }
    }
}