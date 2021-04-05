package com.unidavi.tc.conto

import android.content.Context
import androidx.lifecycle.LiveData
import com.unidavi.tc.conto.database.*
import kotlin.random.Random
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer


fun populateDatabase(context: Context) {
    val db = ContoDataBase.getInstance(context)
    if (db.discountDao.getActiveDiscountsNoLiveData().isEmpty()) {
        db.clearAllTables()
        val establishmentList = List(10) { Establishment() }
        val productList = List(30) { Product() }
        val discountList = List(20) { Discount() }
        for (i in establishmentList.indices) {
            val j = i + 1
            establishmentList[i].name = "Estabelecimento: $j"
            establishmentList[i].type = if (i % 2 == 0) TYPE_FOOD else TYPE_ENTERTAINMENT
            establishmentList[i].establishmentId =
                db.establishmentDao.insertEstablishment(establishmentList[i])

        }

        for (i in productList.indices) {
            val j = i + 1
            productList[i].name = "Produto $j"
            productList[i].price = Random.nextDouble(12.0,50.0)
            if (i % 2 == 0) {
                productList[i].type = TYPE_FOOD
                when (Random.nextInt(0,4)) {
                    0 -> {
                        productList[i].category = FoodCategory.ENTRADA_FRIA.intValue
                        productList[i].categoryString = FoodCategory.ENTRADA_FRIA.stringValue
                    }
                    1 -> {
                        productList[i].category = FoodCategory.ENTRADA_QUENTE.intValue
                        productList[i].categoryString = FoodCategory.ENTRADA_QUENTE.stringValue
                    }
                    2 -> {
                        productList[i].category = FoodCategory.PRINCIPAL.intValue
                        productList[i].categoryString = FoodCategory.PRINCIPAL.stringValue
                    }
                    3 -> {
                        productList[i].category = FoodCategory.SOBREMESA.intValue
                        productList[i].categoryString = FoodCategory.SOBREMESA.stringValue
                    }
                    4 -> {
                        productList[i].category = FoodCategory.BEBIDA.intValue
                        productList[i].categoryString = FoodCategory.BEBIDA.stringValue
                    }
                }


            } else {
                productList[i].type = TYPE_ENTERTAINMENT
                when (Random.nextInt(0,2)) {
                    0 -> {
                        productList[i].category = EntertainmentCategory.CINEMA.intValue
                        productList[i].categoryString = EntertainmentCategory.CINEMA.stringValue
                    }
                    1 -> {
                        productList[i].category = EntertainmentCategory.SHOW.intValue
                        productList[i].categoryString = EntertainmentCategory.SHOW.stringValue
                    }
                    2 -> {
                        productList[i].category = EntertainmentCategory.TEATRO.intValue
                        productList[i].categoryString = EntertainmentCategory.TEATRO.stringValue
                    }
                }

            }

            productList[i].productId = db.productDao.insertProduct(productList[i])

        }

        for (i in discountList.indices) {
            val j = i + 1
            discountList[i].percentage = Random.nextInt(10,50)
            discountList[i].active = i % 6 != 0
            discountList[i].description = "Descrição do desconto $j"

            discountList[i].discountId = db.discountDao.insertDiscount(discountList[i])
            discountList[i].code = (generateDiscountCode(5, discountList[i].percentage))


        }

        for (i in discountList.indices) {
            discountList[i].establishmentOwnerId = establishmentList[i % 10].establishmentId
            discountList[i].establishment = establishmentList[i % 10]

            db.discountDao.updateDiscount(discountList[i])
        }

        for (i in productList.indices) {
            productList[i].establishmentOwnerId = establishmentList[i % 10].establishmentId
            productList[i].type = establishmentList[i % 10].type

            db.productDao.updateProduct(productList[i])
        }
    }

}
fun generateDiscountCode(length: Int, percentage:Int) : String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    val code = (1..length)
        .map { allowedChars.random() }
        .joinToString("")
    return "$code-$percentage"
}

