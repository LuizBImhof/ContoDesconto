package com.unidavi.tc.conto.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.unidavi.tc.conto.ContoApplication
import com.unidavi.tc.conto.database.ContoDataBase
import java.lang.IllegalArgumentException

class DiscountViewModelFactory (
    private val dataSource: ContoDataBase,
    private val application: Application,
    private val type: Int,
    private val establishmentId: Long) : ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DiscountViewModel::class.java)){
            return DiscountViewModel(dataSource, application, type, establishmentId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}