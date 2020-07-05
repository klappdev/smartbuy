package org.kl.smartbuy.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val icon: Int,
    val name: String,
    val description: String,
    val products: List<Product>
)