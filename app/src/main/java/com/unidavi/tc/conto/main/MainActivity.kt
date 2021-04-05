package com.unidavi.tc.conto.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.unidavi.tc.conto.navigation.FavoritesActivity
import com.unidavi.tc.conto.navigation.NotificationActivity
import com.unidavi.tc.conto.R
import com.unidavi.tc.conto.adapters.ViewPageAdapter
import com.unidavi.tc.conto.navigation.ProfileActivity
import com.unidavi.tc.conto.navigation.UsedOffersActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolBar_main)
        val toggle = ActionBarDrawerToggle(
            this,
            drawer_main,
            toolBar_main,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawer_main.addDrawerListener(toggle)
        navigation_drawer_main.setNavigationItemSelectedListener (this)
        setUpTabs()
    }

    override fun onBackPressed() {
        if (drawer_main.isDrawerOpen(GravityCompat.START))
            drawer_main.closeDrawer(GravityCompat.START)
        else {
            super.onBackPressed()
        }
    }

    private fun setUpTabs(){
        val adapter = ViewPageAdapter(supportFragmentManager)
        adapter.addFragment(FoodTypeFragment(), "Alimentação")
        adapter.addFragment(EntertainmentTypeFragment(), "Entretenimento")
        viewPager_main.adapter = adapter
        tabs_main.setupWithViewPager(viewPager_main)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.nav_item_profile -> {
                val intent = Intent(this.applicationContext, ProfileActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.nav_item_favorites -> {
                val intent = Intent(this.applicationContext, FavoritesActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.nav_item_notifications -> {
                val intent = Intent(this.applicationContext, NotificationActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.nav_item_used_offers -> {
                val intent = Intent(this.applicationContext, UsedOffersActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return false
        }
    }
}

