package com.unidavi.tc.conto.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.unidavi.tc.conto.DateTypeConverter
import com.unidavi.tc.conto.populateDatabase


@Database(entities = [Discount::class, Establishment::class, Product::class], version = 1)
@TypeConverters(DateTypeConverter::class)
abstract class ContoDataBase : RoomDatabase(){
    abstract val establishmentDao: EstablishmentDao
    abstract val discountDao: DiscountDao
    abstract val productDao: ProductDao

    companion object{
        @Volatile
        private var INSTANCE: ContoDataBase? = null
        fun getInstance(context: Context): ContoDataBase{
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ContoDataBase::class.java,
                        "conto_database"
                    )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}