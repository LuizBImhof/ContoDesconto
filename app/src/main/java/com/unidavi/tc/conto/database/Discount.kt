package com.unidavi.tc.conto.database

import androidx.room.*

@Entity(tableName = "discount_table")
data class Discount(
    @PrimaryKey(autoGenerate = true)
    var discountId: Long = 0L,
    var percentage: Int = -1,
    var active: Boolean = true,
    var favorite: Boolean = false,
    var description: String = "",
    var code: String = "",
    @ColumnInfo(name = "active_until")
    var activeUntil: Long = System.currentTimeMillis(),
    @ForeignKey(
        entity = Establishment::class,
        parentColumns = ["establishment_owner_id"],
        childColumns = ["establishment_id"],
        onDelete = ForeignKey.RESTRICT
    )
    @ColumnInfo(name = "establishment_owner_id")
    var establishmentOwnerId: Long = 0,
    @Embedded var establishment: Establishment = Establishment()
)