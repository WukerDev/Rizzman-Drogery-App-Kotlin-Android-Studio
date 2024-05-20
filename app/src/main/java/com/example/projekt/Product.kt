package com.example.projekt

import java.io.Serializable

data class Product(
    val id: Long,
    val name: String,
    val brand: String,
    val description: String,
    val price: Double,
    val grams: String,
    val pergram: String,
    val imageLink: String,
    val imageLink2: String,
    val imageLink3: String
) : Serializable
