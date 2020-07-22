package org.kl.smartbuy.db.convert

import androidx.room.TypeConverter

class RangeConverter {

    @TypeConverter
    fun fromRange(range: IntRange): String {
        return range.toString()
    }

    @TypeConverter
    fun toRange(data: String): IntRange {
        val parts: List<String> = data.split("..")

        return IntRange(parts[0].toInt(), parts[1].toInt())
    }
}