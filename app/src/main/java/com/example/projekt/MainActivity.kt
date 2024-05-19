package com.example.projekt

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var productContainer: LinearLayout
    private lateinit var reloadButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper = DatabaseHelper(this)
        productContainer = findViewById(R.id.productContainer)
        reloadButton = findViewById(R.id.reloadButton)

        reloadButton.setOnClickListener {
            clearDatabaseAndReload()
        }


        // Load and display products
        loadProducts().forEach { product ->
            addProductView(product)
        }
    }

    private fun loadProducts(): List<Product> {
        val db = databaseHelper.readableDatabase
        val projection = arrayOf(
            DatabaseContract.ProductEntry.ID,
            DatabaseContract.ProductEntry.COLUMN_NAME,
            DatabaseContract.ProductEntry.COLUMN_BRAND,
            DatabaseContract.ProductEntry.COLUMN_DESC,
            DatabaseContract.ProductEntry.COLUMN_PRICE,
            DatabaseContract.ProductEntry.COLUMN_GRAMS,
            DatabaseContract.ProductEntry.COLUMN_PERGRAM,
            DatabaseContract.ProductEntry.COLUMN_IMAGELINK
        )
        val cursor = db.query(
            DatabaseContract.ProductEntry.TABLE_NAME,
            projection,
            null, null, null, null, null
        )

        val products = mutableListOf<Product>()
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(DatabaseContract.ProductEntry.ID))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.ProductEntry.COLUMN_NAME))
                val brand = getString(getColumnIndexOrThrow(DatabaseContract.ProductEntry.COLUMN_BRAND))
                val description = getString(getColumnIndexOrThrow(DatabaseContract.ProductEntry.COLUMN_DESC))
                val price = getDouble(getColumnIndexOrThrow(DatabaseContract.ProductEntry.COLUMN_PRICE))
                val grams = getString(getColumnIndexOrThrow(DatabaseContract.ProductEntry.COLUMN_GRAMS))
                val pergram = getString(getColumnIndexOrThrow(DatabaseContract.ProductEntry.COLUMN_PERGRAM))
                val imageLink = getString(getColumnIndexOrThrow(DatabaseContract.ProductEntry.COLUMN_IMAGELINK))
                products.add(Product(id, name, brand, description, price, grams, pergram, imageLink))
            }
        }
        cursor.close()
        return products
    }

    private fun addProductView(product: Product) {
        val productView = layoutInflater.inflate(R.layout.product_item, productContainer, false)
        val nameTextView = productView.findViewById<TextView>(R.id.nameTextView)
        val brandTextView = productView.findViewById<TextView>(R.id.brandTextView)
        val descriptionTextView = productView.findViewById<TextView>(R.id.descriptionTextView)
        val gramsTextView = productView.findViewById<TextView>(R.id.gramsTextView)
        val priceTextView = productView.findViewById<TextView>(R.id.priceTextView)
        val pergramTextView = productView.findViewById<TextView>(R.id.pergramTextView)
        val imageView = productView.findViewById<ImageView>(R.id.imageView)


        nameTextView.text = product.name
        descriptionTextView.text = product.description
        gramsTextView.text = "${product.grams} ml"
        priceTextView.text = "${product.price} zł"
        pergramTextView.text = "${product.pergram} zł za ml"
        brandTextView.text = product.brand

        // Load image using AsyncTask
        LoadImageTask(imageView).execute(product.imageLink)

        productContainer.addView(productView)
    }

    private fun clearDatabaseAndReload() {
        databaseHelper.clearDatabase()
        productContainer.removeAllViews()
        databaseHelper.insertInitialData(databaseHelper.writableDatabase)
        loadProducts().forEach { product ->
            addProductView(product)
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
