package com.example.kmadcourse

data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    var quantity: Int = 0
) {
    fun stringify(): String {
        return "Product(id=$id, title=$title, price=$price, quantity=$quantity)"
    }
}