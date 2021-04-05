package com.unidavi.tc.conto.database

import androidx.room.Entity
import androidx.room.PrimaryKey

const val TYPE_FOOD = 0
const val TYPE_ENTERTAINMENT = 1

@Entity(tableName = "establishment_table")
data class Establishment (

    @PrimaryKey(autoGenerate = true)
    var establishmentId: Long = 0L,
    var name: String = "",
    var type: Int = -1,
)
