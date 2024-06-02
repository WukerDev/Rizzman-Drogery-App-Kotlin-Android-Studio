package com.example.projekt

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var productContainer: LinearLayout
    private lateinit var filterButton: Button
    private lateinit var searchEditText: EditText
    private lateinit var floatingActionButton: FloatingActionButton
    private var allProducts: List<Product> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper = DatabaseHelper(this)
        productContainer = findViewById(R.id.productContainer)
        filterButton = findViewById(R.id.filterButton)
        searchEditText = findViewById(R.id.searchEditText)
        floatingActionButton = findViewById(R.id.floatingActionButton)

        filterButton.setOnClickListener {
            showFilterDialog()
        }

        floatingActionButton.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        // Load and display products
        allProducts = loadProducts()
        allProducts.forEach { product ->
            addProductView(product)
        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterProducts(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
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
            DatabaseContract.ProductEntry.COLUMN_IMAGELINK,
            DatabaseContract.ProductEntry.COLUMN_IMAGELINK2,
            DatabaseContract.ProductEntry.COLUMN_IMAGELINK3
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
                val imageLink2 = getString(getColumnIndexOrThrow(DatabaseContract.ProductEntry.COLUMN_IMAGELINK2))
                val imageLink3 = getString(getColumnIndexOrThrow(DatabaseContract.ProductEntry.COLUMN_IMAGELINK3))
                products.add(Product(id, name, brand, description, price, grams, pergram, imageLink, imageLink2, imageLink3))
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
        //val gramsTextView = productView.findViewById<TextView>(R.id.gramsTextView)
        val priceTextView = productView.findViewById<TextView>(R.id.priceTextView)
        val pergramTextView = productView.findViewById<TextView>(R.id.pergramTextView)
        val imageView = productView.findViewById<ImageView>(R.id.imageView)
        val addToCartButton = productView.findViewById<Button>(R.id.addToCartButton)

        nameTextView.text = product.name
        descriptionTextView.text = product.description
        //gramsTextView.text = "${product.grams} ml"
        priceTextView.text = "${product.price} zł"
        pergramTextView.text = "${product.pergram} zł za 100 ml"
        brandTextView.text = product.brand

        // Load image using AsyncTask
        LoadImageTask(imageView).execute(product.imageLink)

        addToCartButton.setOnClickListener {
            CartManager.addProduct(product)
            Toast.makeText(this, "${product.name} został dodany do koszyka", Toast.LENGTH_SHORT).show()
        }

        // Set click listener to navigate to details view
        imageView.setOnClickListener {
            val intent = Intent(this, ProductDetailsActivity::class.java)
            intent.putExtra("product", product)
            startActivity(intent)
        }

        productContainer.addView(productView)
    }

    private fun clearDatabaseAndReload() {
        databaseHelper.clearDatabase()
        productContainer.removeAllViews()
        databaseHelper.insertInitialData(databaseHelper.writableDatabase)
        allProducts = loadProducts()
        allProducts.forEach { product ->
            addProductView(product)
        }
    }

    private fun showFilterDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_filters, null)
        val radioGroup = dialogView.findViewById<RadioGroup>(R.id.filterRadioGroup)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Filtruj Produkty")
            .setView(dialogView)
            .setPositiveButton("Zastosuj") { _, _ ->
                applyFilter(radioGroup.checkedRadioButtonId)
            }
            .setNegativeButton("Anuluj", null)
            .create()

        dialog.show()
    }

    private fun applyFilter(checkedId: Int) {
        val filteredProducts = when (checkedId) {
            R.id.filterPriceLowToHigh -> allProducts.sortedBy { it.price }
            R.id.filterPriceHighToLow -> allProducts.sortedByDescending { it.price }
            R.id.filterBrandAtoZ -> allProducts.sortedBy { it.brand }
            R.id.filterBrandZtoA -> allProducts.sortedByDescending { it.brand }
            R.id.filterNameAtoZ -> allProducts.sortedBy { it.name }
            R.id.filterNameZtoA -> allProducts.sortedByDescending { it.name }
            else -> allProducts
        }

        productContainer.removeAllViews()
        filteredProducts.forEach { product ->
            addProductView(product)
        }
    }

    private fun filterProducts(query: String) {
        val filteredProducts = allProducts.filter { product ->
            product.name.contains(query, ignoreCase = true) ||
                    product.brand.contains(query, ignoreCase = true) ||
                    product.description.contains(query, ignoreCase = true) ||
                    product.price.toString().contains(query, ignoreCase = true)
        }

        productContainer.removeAllViews()
        filteredProducts.forEach { product ->
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
