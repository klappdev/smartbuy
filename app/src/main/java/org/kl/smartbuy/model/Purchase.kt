package org.kl.smartbuy.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "purchase")
data class Purchase(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val date: String
)