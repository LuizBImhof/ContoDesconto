package com.unidavi.tc.conto.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.unidavi.tc.conto.database.ContoDataBase
import timber.log.Timber

class ProductViewModel (val database: ContoDataBase,
                        application: Application,
                        establishmentId: Long) : AndroidViewModel(application) {

    val products = database.productDao.getProductsFromEstablishment(establishmentId)

    init {
        Timber.i("id do estabelecimento :%s", establishmentId.toString())

    }


}