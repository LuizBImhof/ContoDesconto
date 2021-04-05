package com.unidavi.tc.conto.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unidavi.tc.conto.database.ContoDataBase
import kotlinx.coroutines.launch

class DiscountDetailsViewModel(private  val discountKey: Long = 0L,
                               dataSource: ContoDataBase) : ViewModel(){

    val database = dataSource

    private val discount = database.discountDao.getDiscountWithId(discountKey)

    private val _inactivateDiscount = MutableLiveData<Boolean?>()
    val inactivateDiscount: LiveData<Boolean?>
        get() = _inactivateDiscount

    fun getDiscount() = discount

    fun doneInactivatingDiscount(){
        _inactivateDiscount.value = null
    }

    fun onInactivateDiscount (){

        viewModelScope.launch {
            val discount1 = database.discountDao.getDiscountWithIdNoLiveData(discountKey) ?: return@launch
            discount1.active = false
            database.discountDao.updateDiscount(discount1)

            _inactivateDiscount.value = true
        }
    }
}