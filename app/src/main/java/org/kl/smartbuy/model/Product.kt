package org.kl.smartbuy.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import org.kl.smartbuy.db.convert.RangeConverter

@Entity(tableName = "product")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "id_category")
    val idCategory: Int,

    val name: String,
    val description: String,
    val price: Double,

    @field:TypeConverters(RangeConverter::class)
    val range: IntRange,
    val measure: String,

    @ColumnInfo(name = "icon_url")
    val iconUrl: String = ""
)