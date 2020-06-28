package org.kl.smartbuy.model

data class Product(val id: Int,
                   val icon: Int,
                   val name: String,
                   val description: String,
                   val price: Double,
                   val range: IntRange,
                   val measure: String)