package com.example.finalkotlinapp
data class Order(
    val id: String = "", // Add this property to uniquely identify the order
    val customerName: String = "",
    val totalPay: String = "",
    val Quantity: Int = 0,
    val imageUrl: String = ""
)
