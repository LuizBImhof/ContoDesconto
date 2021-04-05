package com.unidavi.tc.conto.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.unidavi.tc.conto.database.ContoDataBase
import com.unidavi.tc.conto.database.Discount

class DiscountViewModel(
    dataBase: ContoDataBase,
    application: Application,
    type: Int,
    discountId: Long
) : AndroidViewModel(application) {
    val establishmentId = dataBase.discountDao.getEstablishmentIdFromDiscount(discountId)
    val discounts = when (type){
        -1 -> establishmentId?.let {
            dataBase.discountDao.getAllDifferentActiveDiscountsFromEstablishment(
                establishmentId,
                discountId
            )
        }
        -2 -> dataBase.discountDao.getFavoritesDiscounts()
        -3 ->dataBase.discountDao.getInactiveDiscounts()
        else -> dataBase.discountDao.getAllActiveDiscountsFromEstablishmentType(type)
    }
    private val _navigateToDiscountDetail = MutableLiveData<Discount>()
    val navigateToDiscountDetail
        get() = _navigateToDiscountDetail

    fun onDiscountClicked(discount: Discount) {
        _navigateToDiscountDetail.value = discount
    }

    fun onDiscountDetailNavigated() {
        _navigateToDiscountDetail.value = null
    }
}