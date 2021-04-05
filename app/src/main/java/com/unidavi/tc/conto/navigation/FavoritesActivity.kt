package com.unidavi.tc.conto.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.unidavi.tc.conto.R
import kotlinx.android.synthetic.main.activity_notification.*

class FavoritesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        setSupportActionBar(toolbar_favorites)
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val fragment = FavoritesFragment()
        fragmentTransaction.add(R.id.fragment_container_favorites, fragment)
        fragmentTransaction.commit()

    }

    override fun setSupportActionBar(toolbar: Toolbar?) {
        super.setSupportActionBar(toolbar)
        toolbar?.setNavigationOnClickListener {
            finish()
        }
    }


}