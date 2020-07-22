package org.kl.smartbuy.model

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "purchase")
data class Purchase(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @DrawableRes
    val icon: Int,
    val name: String,
    val date: String,
    val selected: Boolean
)