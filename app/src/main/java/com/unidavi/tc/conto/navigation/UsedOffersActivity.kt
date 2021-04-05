package com.unidavi.tc.conto.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.unidavi.tc.conto.R
import kotlinx.android.synthetic.main.activity_used_offers.*

class UsedOffersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_used_offers)
        setSupportActionBar(toolbar_used_offers)
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val fragment = UsedOffersFragment()
        fragmentTransaction.add(R.id.fragment_container_used_offers, fragment)
        fragmentTransaction.commit()
    }

    override fun setSupportActionBar(toolbar: Toolbar?) {
        super.setSupportActionBar(toolbar)
        toolbar?.setNavigationOnClickListener {
            finish()
        }
    }
}