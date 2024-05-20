package com.example.projekt

import android.os.Bundle
import android.widget.Toast
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class ProductDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        val product = intent.getSerializableExtra("product") as? Product

        if (product != null) {
            val viewPager = findViewById<ViewPager2>(R.id.viewPager)
            val nameTextView = findViewById<TextView>(R.id.detailNameTextView)
            val brandTextView = findViewById<TextView>(R.id.detailBrandTextView)
            val descriptionTextView = findViewById<TextView>(R.id.detailDescriptionTextView)
            val priceTextView = findViewById<TextView>(R.id.detailPriceTextView)
            val gramsTextView = findViewById<TextView>(R.id.detailGramsTextView)
            val pergramTextView = findViewById<TextView>(R.id.detailPergramTextView)
            val addToCartButton = findViewById<Button>(R.id.addToCartButton)


            nameTextView.text = product.name
            brandTextView.text = product.brand
            descriptionTextView.text = product.description
            priceTextView.text = "${product.price} zł"
            gramsTextView.text = "${product.grams} ml"
            pergramTextView.text = "${product.pergram} zł za 100 ml"

            val imageUrls = listOf(product.imageLink, product.imageLink2, product.imageLink3)
            val adapter = ImageSliderAdapter(imageUrls)
            viewPager.adapter = adapter

            addToCartButton.setOnClickListener {
                CartManager.addProduct(product)
                Toast.makeText(this, "Dodano do koszyka", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
