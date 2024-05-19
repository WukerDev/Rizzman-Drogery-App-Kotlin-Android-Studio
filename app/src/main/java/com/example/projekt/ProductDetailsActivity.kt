package com.example.projekt

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.Toast
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.net.HttpURLConnection
import java.net.URL

class ProductDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        val product = intent.getSerializableExtra("product") as? Product

        if (product != null) {
            val imageView = findViewById<ImageView>(R.id.detailImageView)
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

            // Load image using AsyncTask
            LoadImageTask(imageView).execute(product.imageLink)

            addToCartButton.setOnClickListener {
                CartManager.addProduct(product)
                Toast.makeText(this, "Dodano do koszyka", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private class LoadImageTask(val imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
        override fun doInBackground(vararg params: String?): Bitmap? {
            return try {
                val url = URL(params[0])
                val connection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val inputStream = connection.inputStream
                BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        override fun onPostExecute(result: Bitmap?) {
            if (result != null) {
                imageView.setImageBitmap(result)
            } else {
                imageView.setImageResource(R.drawable.placeholder_image) // Set a placeholder image in case of failure
            }
        }
    }
}
