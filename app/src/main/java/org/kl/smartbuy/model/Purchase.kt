package org.kl.smartbuy.model

data class Purchase(val id: Int,
                    val icon: Int,
                    val name: String,
                    val date: String,
                    val products: List<Product>,
                    val selected: Boolean)