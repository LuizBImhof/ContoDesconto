package com.unidavi.tc.conto.database

import androidx.room.*

@Entity(tableName = "product_table")
data class Product (
    @PrimaryKey(autoGenerate = true)
    var productId: Long = 0L,
    var type: Int = -1,
    var price: Double = -1.0,
    var name: String = "",
    var category: Int = 0,
    @ColumnInfo(name = "category_string")
    var categoryString: String = "",
    @ForeignKey(
        entity = Establishment::class,
        parentColumns = ["establishment_id"],
        childColumns = ["establishment_owner_id"],
        onDelete = ForeignKey.RESTRICT
    )
    @ColumnInfo(name = "establishment_owner_id")
    var establishmentOwnerId: Long = 0,
    @ForeignKey(
        entity = Discount::class,
        parentColumns = ["discount_id"],
        childColumns = ["product_discount_id"],
        onDelete = ForeignKey.RESTRICT
    )
    @ColumnInfo(name = "product_discount_id")
    var discountId: Long = 0
)

enum class FoodCategory (val intValue: Int, val stringValue: String){
    ENTRADA_FRIA(1, "Entrada Fria"),
    ENTRADA_QUENTE(2, "Entrada Quente"),
    PRINCIPAL(3, "Prato Principal"),
    SOBREMESA(4, "Sobremesa"),
    BEBIDA(5, "Bebida")
}
enum class EntertainmentCategory (val intValue: Int, val stringValue: String){
    SHOW(1, "Show"),
    TEATRO(2, "Teatro"),
    CINEMA(3, "Cinema")
}