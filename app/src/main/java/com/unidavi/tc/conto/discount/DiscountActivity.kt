package com.unidavi.tc.conto.discount

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.unidavi.tc.conto.R
import com.unidavi.tc.conto.adapters.ViewPageAdapter
import com.unidavi.tc.conto.database.ContoDataBase
import com.unidavi.tc.conto.database.Discount
import com.unidavi.tc.conto.main.MainActivity
import kotlinx.android.synthetic.main.activity_discount.*
import timber.log.Timber

class DiscountActivity : AppCompatActivity() {

    lateinit var discount: Discount
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discount)
        val intent = intent
        val discountKey = intent.extras?.getLong("discountKey")

        Timber.i("""Id do desconto $discountKey""")
        discount = discountKey?.let {
            ContoDataBase.getInstance(application).discountDao.getDiscountWithIdNoLiveData(
                it
            )
        }!!
        setUpTabs()

        onPrepareOptionsMenu(toolBar_discount.menu)
        setSupportActionBar(toolBar_discount)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (discount.favorite)
            menu?.findItem(R.id.favorite)?.icon = ContextCompat.getDrawable(
                this,
                R.drawable.ic_favorite_24dp
            )
        return true;
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar_establishment, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun setSupportActionBar(toolbar: Toolbar?) {
        super.setSupportActionBar(toolbar)
        supportActionBar?.title = discount.establishment.name
        toolbar?.menu?.findItem(R.id.favorite)?.isVisible = true
        toolbar?.setNavigationOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite -> {
                if (discount.favorite) {
                    item.icon = ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_favorite_inactive_24dp
                    )
                    discount.favorite = false
                } else {
                    item.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_24dp)
                    discount.favorite = true
                }
                ContoDataBase.getInstance(application).discountDao.updateDiscount(discount)

                Timber.i("Desconto %s favoritado ", discount.discountId.toString())
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setUpTabs() {
        val adapter = ViewPageAdapter(supportFragmentManager)
        adapter.addFragment(DetailsFragment(), "Detalhes")
        adapter.addFragment(MenuFragment(), "Produtos")
        adapter.addFragment(MoreDiscountsFragment(), "Mais ofertas")
        viewPager_discount.adapter = adapter
        tabs_discount.setupWithViewPager(viewPager_discount)
    }


}

