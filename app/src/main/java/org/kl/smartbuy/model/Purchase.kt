package org.kl.smartbuy.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "purchase")
data class Purchase(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val icon: Int,
    val name: String,
    val date: String,
    val products: List<Product>,
    val selected: Boolean
)