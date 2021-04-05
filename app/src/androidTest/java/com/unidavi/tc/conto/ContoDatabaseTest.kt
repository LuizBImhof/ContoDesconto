package com.unidavi.tc.conto

import android.os.Handler
import android.os.Looper
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.unidavi.tc.conto.database.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.lang.Exception
import kotlin.jvm.Throws
import org.junit.Assert.assertEquals
import org.junit.Rule
import timber.log.Timber
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class ContoDatabaseTest : InstantTaskExecutorRule() {

    private lateinit var establishmentDao: EstablishmentDao
    private lateinit var discountDao: DiscountDao
    private lateinit var productDao: ProductDao
    private lateinit var db: ContoDataBase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, ContoDataBase::class.java)
            .allowMainThreadQueries()
            .build()
        establishmentDao = db.establishmentDao
        discountDao = db.discountDao
        productDao = db.productDao

    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndUpdateAllTables() {
        Timber.i("Insert and get all tables test beginning")
        val establishment = Establishment()
        establishment.name = "Estabelecimento1"
        val discount = Discount()
        discount.percentage = 10
        val product = Product()
        product.name = "produto"

        establishment.establishmentId = establishmentDao.insertEstablishment(establishment)
        discount.discountId = discountDao.insertDiscount(discount)
        product.productId = productDao.insertProduct(product)

        var lastEstablishment =
            establishmentDao.getEstablishmentWithId(establishment.establishmentId)
        var lastDiscount = discountDao.getDiscountWithId(discount.discountId)
        var lastProduct = productDao.getProductWithId(product.productId)

        lastEstablishment.observeOnce { assertEquals(it?.name, "Estabelecimento1") }
        lastDiscount.observeOnce { assertEquals(it?.percentage, 10) }
        assertEquals(lastProduct?.name, "produto")

        establishment.name = "Estabelecimento2"
        establishmentDao.updateEstablishment(establishment)

        discount.percentage = 15
        discountDao.updateDiscount(discount)

        product.name = "produto2"
        productDao.updateProduct(product)

        lastEstablishment = establishmentDao.getEstablishmentWithId(establishment.establishmentId)
        lastDiscount = discountDao.getDiscountWithId(discount.discountId)
        lastProduct = productDao.getProductWithId(product.productId)

        lastEstablishment.observeOnce { assertEquals(it?.name, "Estabelecimento2") }
        lastDiscount.observeOnce { assertEquals(it?.percentage, 15) }
        assertEquals(lastProduct?.name, "produto2")

        Timber.i("Insert and get all tables test ending")
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetActiveDiscounts() {
        Timber.i("Insert and get all active discounts test beginning")

        val activeDiscount = Discount()
        val inactiveDiscount = Discount()

        activeDiscount.discountId = discountDao.insertDiscount(activeDiscount)
        inactiveDiscount.discountId = discountDao.insertDiscount(inactiveDiscount)
        var activeDiscounts = discountDao.getActiveDiscounts()
        var inactiveDiscounts = discountDao.getInactiveDiscounts()

        activeDiscounts.observeOnce {assertEquals(2, it.size)}
        inactiveDiscounts.observeOnce {assertEquals(0, it.size)}

        inactiveDiscount.active = false
        discountDao.updateDiscount(inactiveDiscount)

        activeDiscounts = discountDao.getActiveDiscounts()
        inactiveDiscounts = discountDao.getInactiveDiscounts()

        activeDiscounts.observeOnce {assertEquals(1, it.size)}
        inactiveDiscounts.observeOnce {assertEquals(1, it.size)}
        // observeOnce usado para garantir que o android retorne o valor correto do livedata,
        // que é criado "lazily" assim é forçado a aguardar o retorno
        Timber.i("Insert and get all active discounts test ending")
    }

    @Test
    @Throws(Exception::class)
    fun testForeignKeys() {
        Timber.i("Foreign Key test beginning")
        val establishment1 = Establishment()
        establishment1.type = TYPE_FOOD
        establishment1.establishmentId = establishmentDao.insertEstablishment(establishment1)
        val product = Product()
        product.establishmentOwnerId = establishment1.establishmentId

        val discount = Discount()
        discount.percentage = 15
        discount.establishmentOwnerId = establishment1.establishmentId

        val discount2 = Discount()
        discount2.percentage = 10
        discount2.establishmentOwnerId = establishment1.establishmentId
        discount2.discountId = discountDao.insertDiscount(discount2)

        product.discountId = discount.discountId

        discount.discountId = discountDao.insertDiscount(discount)

        productDao.insertProduct(product)

        val products = productDao.getProductsFromEstablishment(establishment1.establishmentId)
        val foodDiscounts = discountDao.getAllActiveDiscountsFromEstablishmentType(TYPE_FOOD)
        val establishmentDiscounts = discountDao.getAllDifferentActiveDiscountsFromEstablishment(
            establishment1.establishmentId, discount2.discountId
        )

        products.observeOnce { assertEquals(it.size, 1) }
        foodDiscounts.observeOnce { assertEquals(it.size, 2) }
        establishmentDiscounts.observeOnce { assertEquals(it.size, 1) }

        Timber.i("Foreign Key test ending")
    }

    @Test
    @Throws(Exception::class)
    fun testFavoriteDiscounts() {
        Timber.i("Favorites test beginning")
        val discount = Discount()
        val discount2 = Discount()

        discount.favorite = true
        discount.discountId = discountDao.insertDiscount(discount)
        discount2.discountId = discountDao.insertDiscount(discount2)

        var favoriteDiscounts = discountDao.getFavoritesDiscounts()

        favoriteDiscounts.observeOnce { assertEquals(1, it.size) }
        discount2.favorite = true
        discountDao.updateDiscount(discount2)
        favoriteDiscounts = discountDao.getFavoritesDiscounts()
        favoriteDiscounts.observeOnce { assertEquals(2, it.size) }
        Timber.i("Favorites test ending")
    }

    private fun <T> LiveData<T>.observeOnce(onChangeHandler: (T) -> Unit) {
        val observer = OneTimeObserver(handler = onChangeHandler)
        observe(observer, observer)
    }
}