package com.progressifff.bookfinder.presentation

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.progressifff.bookfinder.Constants
import com.progressifff.bookfinder.R

class SearchFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.search_fragment, container, false)
        v.findViewById<Button>(R.id.findBookBtn).setOnClickListener {

            val title = v.findViewById<TextInputEditText>(R.id.title).text.toString()
            val author = v.findViewById<TextInputEditText>(R.id.author).text.toString()
            val isbn = v.findViewById<TextInputEditText>(R.id.isbn).text.toString()

            if(title == "" && author == "" && isbn == ""){
                Toast.makeText(context, R.string.search_data_error, Toast.LENGTH_SHORT).show()
            }
            else{
                val startActivityIntent = Intent(context, SearchedBooksListActivity::class.java)
                startActivityIntent.putExtra(Constants.TITLE_KEY, title)
                startActivityIntent.putExtra(Constants.AUTHOR_KEY, author)
                startActivityIntent.putExtra(Constants.ISBN_KEY, isbn)
                startActivity(startActivityIntent)
            }
        }
        return v
    }
}