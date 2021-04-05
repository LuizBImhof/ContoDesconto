package com.unidavi.tc.conto.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.unidavi.tc.conto.ContoApplication
import com.unidavi.tc.conto.database.ContoDataBase


class DiscountDetailsViewModelFactory(
    private val discountKey: Long,
    private val dataSource: ContoDataBase): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DiscountDetailsViewModel::class.java)) {
            return DiscountDetailsViewModel(discountKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}