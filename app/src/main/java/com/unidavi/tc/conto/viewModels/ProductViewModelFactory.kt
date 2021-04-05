package com.unidavi.tc.conto.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.unidavi.tc.conto.database.ContoDataBase

class ProductViewModelFactory(
    private val dataSource: ContoDataBase,
    private val application: Application,
    private val establishmentId: Long
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            return ProductViewModel(dataSource, application, establishmentId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}