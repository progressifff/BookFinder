package com.progressifff.bookfinder.presentation.models

import com.progressifff.bookfinder.data.BookInfoDTO

data class BookInfo(var pagesCount: Int = 0, var publishers: ArrayList<String> = arrayListOf()){
    companion object {
        fun fromBookInfoDTO(bookInfoDTO: BookInfoDTO) = BookInfo(if(bookInfoDTO.pagesCount != null) bookInfoDTO.pagesCount.toInt() else 0, ArrayList(bookInfoDTO.publishers!!))
    }
}