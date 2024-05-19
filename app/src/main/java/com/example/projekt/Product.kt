package com.example.projekt

data class Product(
    val id: Long,
    val name: String,
    val brand: String,
    val description: String,
    val price: Double,
    val grams: String,
    val pergram: String,
    val imageLink: String
)
