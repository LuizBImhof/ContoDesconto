package com.unidavi.tc.conto.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DiscountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDiscount(discount: Discount) : Long

    @Update()
    fun updateDiscount(discount: Discount)

    @Delete
    suspend fun deleteDiscount(discount: Discount)

    @Query("SELECT * FROM discount_table WHERE active == :active")
    fun getActiveDiscounts(active: Boolean = true): LiveData<List<Discount>>

    @Query("SELECT * FROM discount_table WHERE active == :active")
    fun getActiveDiscountsNoLiveData(active: Boolean = true): List<Discount>

    @Query("SELECT * FROM discount_table WHERE active == :inactive")
    fun getInactiveDiscounts(inactive: Boolean = false): LiveData<List<Discount>>

    @Query("SELECT * FROM discount_table WHERE discountId = :key")
    fun getDiscountWithId(key: Long): LiveData<Discount?>

    @Query("SELECT * FROM discount_table WHERE discountId = :key")
    fun getDiscountWithIdNoLiveData(key: Long): Discount?

    @Query("SELECT A.*  FROM discount_table A " +
            " INNER JOIN establishment_table B ON B.establishmentId = A.establishment_owner_id " +
            " WHERE B.type = :type AND A.ACTIVE = :active" +
            " ORDER BY A.discountId ")
    fun getAllActiveDiscountsFromEstablishmentType(type: Int, active: Boolean = true): LiveData<List<Discount>>

    @Query("SELECT * FROM discount_table WHERE establishment_owner_id = :establishmentKey AND discountId <> :discountKey")
    fun getAllDifferentActiveDiscountsFromEstablishment(establishmentKey: Long, discountKey: Long): LiveData<List<Discount>>

    @Query("SELECT establishment_owner_id FROM discount_table WHERE discountId = :key")
    fun getEstablishmentIdFromDiscount (key: Long): Long?

    @Query("SELECT * FROM discount_table WHERE favorite = :favorite ORDER BY discountId" )
    fun getFavoritesDiscounts(favorite: Boolean = true): LiveData<List<Discount>>
}

@Dao
interface EstablishmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEstablishment(establishment: Establishment) : Long

    @Update
    fun updateEstablishment(establishment: Establishment)

    @Delete
    fun deleteEstablishment(establishment: Establishment)

    @Query("SELECT * FROM establishment_table WHERE establishmentId = :key")
    fun getEstablishmentWithId(key: Long): LiveData<Establishment?>
}

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: Product) : Long

    @Update
    fun updateProduct(product: Product)

    @Delete
    fun deleteProduct(product: Product)

    @Query("SELECT * FROM product_table WHERE productId = :key")
    fun getProductWithId(key: Long): Product?

    @Query("SELECT * FROM product_table WHERE establishment_owner_id = :establishmentKey ORDER BY category")
    fun getProductsFromEstablishment(establishmentKey: Long): LiveData<List<Product>>
}