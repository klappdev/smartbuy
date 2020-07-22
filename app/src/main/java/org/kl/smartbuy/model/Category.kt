package org.kl.smartbuy.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val description: String,

    @ColumnInfo(name = "icon_url")
    val iconUrl: String = ""
)