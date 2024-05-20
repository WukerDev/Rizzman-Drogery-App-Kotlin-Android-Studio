package com.example.projekt

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "ProductDatabase.db"
        const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_PRODUCTS_TABLE = ("CREATE TABLE " + DatabaseContract.ProductEntry.TABLE_NAME + " ("
                + DatabaseContract.ProductEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DatabaseContract.ProductEntry.COLUMN_NAME + " TEXT,"
                + DatabaseContract.ProductEntry.COLUMN_BRAND + " TEXT,"
                + DatabaseContract.ProductEntry.COLUMN_DESC + " TEXT,"
                + DatabaseContract.ProductEntry.COLUMN_PRICE + " REAL,"
                + DatabaseContract.ProductEntry.COLUMN_GRAMS + " TEXT,"
                + DatabaseContract.ProductEntry.COLUMN_PERGRAM + " TEXT,"
                + DatabaseContract.ProductEntry.COLUMN_IMAGELINK + " TEXT,"
                + DatabaseContract.ProductEntry.COLUMN_IMAGELINK2 + " TEXT,"
                + DatabaseContract.ProductEntry.COLUMN_IMAGELINK3 + " TEXT" + ")")
        db.execSQL(CREATE_PRODUCTS_TABLE)
        insertInitialData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.ProductEntry.TABLE_NAME}")
        onCreate(db)
    }

    fun insertInitialData(db: SQLiteDatabase) {
        val products = arrayOf(
            ContentValues().apply {
                put(DatabaseContract.ProductEntry.COLUMN_NAME, "Luminous 630")
                put(DatabaseContract.ProductEntry.COLUMN_BRAND, "NIVEA")
                put(DatabaseContract.ProductEntry.COLUMN_DESC, "Krem do twarzy CC, 3w1, light 01, SPF30")
                put(DatabaseContract.ProductEntry.COLUMN_PRICE, 59.99)
                put(DatabaseContract.ProductEntry.COLUMN_GRAMS, 40)
                put(DatabaseContract.ProductEntry.COLUMN_PERGRAM, 149.98)
                put(DatabaseContract.ProductEntry.COLUMN_IMAGELINK, "https://pro-fra-s3-productsassets.rossmann.pl/product_7_large/2077657_1280_720_1713435779.png")
                put(DatabaseContract.ProductEntry.COLUMN_IMAGELINK2, "https://pro-fra-s3-productsassets.rossmann.pl/product_3_large/381360_1280_720_1709189045.png")
                put(DatabaseContract.ProductEntry.COLUMN_IMAGELINK3, "https://pro-fra-s3-productsassets.rossmann.pl/product_6_large/381360_1280_720_1709188623.png")

            },
            ContentValues().apply {
                put(DatabaseContract.ProductEntry.COLUMN_NAME, "Krem i puder do twarzy")
                put(DatabaseContract.ProductEntry.COLUMN_BRAND, "ISANA")
                put(DatabaseContract.ProductEntry.COLUMN_DESC, "Krem i puder do twarzy, 2w1, antybakteryjny, Jasny")
                put(DatabaseContract.ProductEntry.COLUMN_PRICE, 8.99)
                put(DatabaseContract.ProductEntry.COLUMN_GRAMS, 9)
                put(DatabaseContract.ProductEntry.COLUMN_PERGRAM, 99.89)
                put(DatabaseContract.ProductEntry.COLUMN_IMAGELINK, "https://pro-fra-s3-productsassets.rossmann.pl/product_1_large/251571_1280_720_1709158726.png")
                put(DatabaseContract.ProductEntry.COLUMN_IMAGELINK2, "https://pro-fra-s3-productsassets.rossmann.pl/product_2_large/251566_1280_720_1709138556.png")
                put(DatabaseContract.ProductEntry.COLUMN_IMAGELINK3, "https://pro-fra-s3-productsassets.rossmann.pl/product_1_large/251571_1280_720_1709158726.png")

            },
            ContentValues().apply {
                put(DatabaseContract.ProductEntry.COLUMN_NAME, "Eyebrow Expert")
                put(DatabaseContract.ProductEntry.COLUMN_BRAND, "DELIA COSMETICS")
                put(DatabaseContract.ProductEntry.COLUMN_DESC, "Henna do brwi, jednoskładnikowa, ekspresowa, nr 1.0 Czarny")
                put(DatabaseContract.ProductEntry.COLUMN_PRICE, 22.99)
                put(DatabaseContract.ProductEntry.COLUMN_GRAMS, 6)
                put(DatabaseContract.ProductEntry.COLUMN_PERGRAM, 383.17)
                put(DatabaseContract.ProductEntry.COLUMN_IMAGELINK, "https://pro-fra-s3-productsassets.rossmann.pl/product_1_large/279994_1280_720_1709153893.png")
                put(DatabaseContract.ProductEntry.COLUMN_IMAGELINK2, "https://pro-fra-s3-productsassets.rossmann.pl/product_2_large/279992_1280_720_1709148194.png")
                put(DatabaseContract.ProductEntry.COLUMN_IMAGELINK3, "https://pro-fra-s3-productsassets.rossmann.pl/product_3_large/279992_1280_720_1709148195.png")

            },
            ContentValues().apply {
                put(DatabaseContract.ProductEntry.COLUMN_NAME, "Żel do brwi")
                put(DatabaseContract.ProductEntry.COLUMN_BRAND, "GOT2B")
                put(DatabaseContract.ProductEntry.COLUMN_DESC, "Żel do brwi i baby hair")
                put(DatabaseContract.ProductEntry.COLUMN_PRICE, 35.99)
                put(DatabaseContract.ProductEntry.COLUMN_GRAMS, 16)
                put(DatabaseContract.ProductEntry.COLUMN_PERGRAM, 224.94)
                put(DatabaseContract.ProductEntry.COLUMN_IMAGELINK, "https://pro-fra-s3-productsassets.rossmann.pl/product_1_large/2073580_1280_720_1709182882.png")
                put(DatabaseContract.ProductEntry.COLUMN_IMAGELINK2, "https://pro-fra-s3-productsassets.rossmann.pl/product_2_large/2073580_1280_720_1709213897.png")
                put(DatabaseContract.ProductEntry.COLUMN_IMAGELINK3, "https://pro-fra-s3-productsassets.rossmann.pl/product_1_large/2073580_1280_720_1709182882.png")

            },
            ContentValues().apply {
                put(DatabaseContract.ProductEntry.COLUMN_NAME, "Maestria")
                put(DatabaseContract.ProductEntry.COLUMN_BRAND, "DERMIKA")
                put(DatabaseContract.ProductEntry.COLUMN_DESC, "Krem do twarzy przeciwzmarszczkowy, na dzień i na noc 50+")
                put(DatabaseContract.ProductEntry.COLUMN_PRICE, 59.99)
                put(DatabaseContract.ProductEntry.COLUMN_GRAMS, 50)
                put(DatabaseContract.ProductEntry.COLUMN_PERGRAM, 119.98)
                put(DatabaseContract.ProductEntry.COLUMN_IMAGELINK, "https://pro-fra-s3-productsassets.rossmann.pl/product_7_large/2077409_1280_720_1709190652.png")
                put(DatabaseContract.ProductEntry.COLUMN_IMAGELINK2, "https://pro-fra-s3-productsassets.rossmann.pl/product_1_large/2077408_1280_720_1709190656.png")
                put(DatabaseContract.ProductEntry.COLUMN_IMAGELINK3, "https://pro-fra-s3-productsassets.rossmann.pl/product_2_large/2077408_1280_720_1709215323.png")

            },
            ContentValues().apply {
                put(DatabaseContract.ProductEntry.COLUMN_NAME, "Fresh Endurance")
                put(DatabaseContract.ProductEntry.COLUMN_BRAND, "ADIDAS")
                put(DatabaseContract.ProductEntry.COLUMN_DESC, "Antyperspirant w kulce, 72h")
                put(DatabaseContract.ProductEntry.COLUMN_PRICE, 12.99)
                put(DatabaseContract.ProductEntry.COLUMN_GRAMS, 50)
                put(DatabaseContract.ProductEntry.COLUMN_PERGRAM, 25.98)
                put(DatabaseContract.ProductEntry.COLUMN_IMAGELINK, "https://pro-fra-s3-productsassets.rossmann.pl/product_1_large/408621_1280_720_1709210564.png")
                put(DatabaseContract.ProductEntry.COLUMN_IMAGELINK2, "https://pro-fra-s3-productsassets.rossmann.pl/product_2_large/408621_1280_720_1709210562.png")
                put(DatabaseContract.ProductEntry.COLUMN_IMAGELINK3, "https://pro-fra-s3-productsassets.rossmann.pl/product_1_large/408621_1280_720_1709210564.png")

            },
            ContentValues().apply {
                put(DatabaseContract.ProductEntry.COLUMN_NAME, "Hawajski Klimat")
                put(DatabaseContract.ProductEntry.COLUMN_BRAND, "MANUFAKTURA PIĘKNA")
                put(DatabaseContract.ProductEntry.COLUMN_DESC, "Mgiełka do ciała")
                put(DatabaseContract.ProductEntry.COLUMN_PRICE, 16.49)
                put(DatabaseContract.ProductEntry.COLUMN_GRAMS, 150)
                put(DatabaseContract.ProductEntry.COLUMN_PERGRAM, 10.99)
                put(DatabaseContract.ProductEntry.COLUMN_IMAGELINK, "https://pro-fra-s3-productsassets.rossmann.pl/product_1_large/351427_1280_720_1709183053.png")
                put(DatabaseContract.ProductEntry.COLUMN_IMAGELINK2, "https://pro-fra-s3-productsassets.rossmann.pl/product_2_large/351427_1280_720_1709178967.png")
                put(DatabaseContract.ProductEntry.COLUMN_IMAGELINK3, "https://pro-fra-s3-productsassets.rossmann.pl/product_3_large/351427_1280_720_1709178969.png")

            },
            ContentValues().apply {
                put(DatabaseContract.ProductEntry.COLUMN_NAME, "Olej arganowy")
                put(DatabaseContract.ProductEntry.COLUMN_BRAND, "ARGANOVE")
                put(DatabaseContract.ProductEntry.COLUMN_DESC, "Olej arganowy, odżywiający, nawilżający, ujędrniający, 100% naturalny")
                put(DatabaseContract.ProductEntry.COLUMN_PRICE, 10.99)
                put(DatabaseContract.ProductEntry.COLUMN_GRAMS, 30)
                put(DatabaseContract.ProductEntry.COLUMN_PERGRAM, 36.63)
                put(DatabaseContract.ProductEntry.COLUMN_IMAGELINK, "https://pro-fra-s3-productsassets.rossmann.pl/product_1_large/323214_1280_720_1709157698.png")
                put(DatabaseContract.ProductEntry.COLUMN_IMAGELINK2, "https://pro-fra-s3-productsassets.rossmann.pl/product_2_large/323214_1280_720_1709157701.png")
                put(DatabaseContract.ProductEntry.COLUMN_IMAGELINK3, "https://pro-fra-s3-productsassets.rossmann.pl/product_1_large/323214_1280_720_1709157698.png")

            },
        )


        for (product in products) {
            db.insert(DatabaseContract.ProductEntry.TABLE_NAME, null, product)
        }
    }
    fun clearDatabase() {
        val db = writableDatabase
        db.execSQL("DELETE FROM ${DatabaseContract.ProductEntry.TABLE_NAME}")
    }
}
