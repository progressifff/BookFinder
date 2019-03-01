package com.progressifff.bookfinder.presentation

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.progressifff.bookfinder.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        if(navigation.selectedItemId != item.itemId){
            setFragment(item.itemId)
            return@OnNavigationItemSelectedListener true
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolBar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolBar)
        if(savedInstanceState == null){
            setFragment(R.id.navigation_search)
        }
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private fun setFragment(@IdRes navigationTabId: Int){
        val tag = navigationTabId.toString()
        val fragment = supportFragmentManager.findFragmentByTag(tag)
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            for(fragmentItem in supportFragmentManager.fragments){
                transaction.hide(fragmentItem)
            }
            transaction.show(fragment).commitNow()
        }
        else{
            when (navigationTabId) {
                R.id.navigation_search -> supportFragmentManager.beginTransaction().add(R.id.container, SearchFragment(), tag).commit()
                R.id.navigation_favorites -> supportFragmentManager.beginTransaction().add(R.id.container, FavoritesBooksFragment(), tag).commit()
                R.id.navigation_settings -> supportFragmentManager.beginTransaction().add(R.id.container, SettingsFragment(), tag).commit()
            }
        }
    }
}
