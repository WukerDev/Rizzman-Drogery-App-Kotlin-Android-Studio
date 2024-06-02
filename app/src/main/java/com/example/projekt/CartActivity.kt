package com.example.projekt

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.net.HttpURLConnection
import java.net.URL

class CartActivity : AppCompatActivity() {

    private lateinit var cartContainer: LinearLayout
    private lateinit var emptyCartTextView: TextView
    private lateinit var totalPriceTextView: TextView
    private lateinit var excludeVatCheckBox: CheckBox

    private var totalPrice: Double = 0.0
    private var isVatExcluded: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        cartContainer = findViewById(R.id.cartContainer)
        emptyCartTextView = findViewById(R.id.emptyCartTextView)
        totalPriceTextView = findViewById(R.id.totalPriceTextView)
        excludeVatCheckBox = findViewById(R.id.excludeVatCheckBox)

        val cartItems = CartManager.getCartItems()
        if (cartItems.isEmpty()) {
            emptyCartTextView.visibility = TextView.VISIBLE
            totalPriceTextView.visibility = TextView.GONE
        } else {
            cartItems.forEach { product ->
                addCartItemView(product)
            }
            totalPrice = CartManager.getTotalPrice()
            updateTotalPrice()
        }

        excludeVatCheckBox.setOnCheckedChangeListener { _, isChecked ->
            isVatExcluded = isChecked
            updateTotalPrice()
        }
    }

    private fun addCartItemView(product: Product) {
        val cartItemView = layoutInflater.inflate(R.layout.cart_item, cartContainer, false)
        val imageView = cartItemView.findViewById<ImageView>(R.id.cartItemImageView)
        val nameTextView = cartItemView.findViewById<TextView>(R.id.cartItemNameTextView)
        val priceTextView = cartItemView.findViewById<TextView>(R.id.cartItemPriceTextView)

        nameTextView.text = product.name
        priceTextView.text = "${product.price} zł"

        // Load image using AsyncTask
        LoadImageTask(imageView).execute(product.imageLink)

        cartContainer.addView(cartItemView)
    }

    private fun updateTotalPrice() {
        val displayPrice = if (isVatExcluded) totalPrice / 1.23 else totalPrice
        val formattedTotalPrice = String.format("%.2f", displayPrice)
        totalPriceTextView.text = "Kwota Końcowa: $formattedTotalPrice zł"
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
