package org.kl.smartbuy.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val icon: Int,
    val name: String,
    val description: String,
    val price: Double,
    val range: IntRange,
    val measure: String
)