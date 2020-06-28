package org.kl.smartbuy.model

data class Category(val id: Int,
                    val icon: Int,
                    val name: String,
                    val description: String,
                    val products: List<Product>)