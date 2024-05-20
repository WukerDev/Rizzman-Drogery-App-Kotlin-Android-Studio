package com.example.projekt

import android.provider.BaseColumns

object DatabaseContract {
    object ProductEntry : BaseColumns {
        const val TABLE_NAME = "products"
        const val COLUMN_NAME = "name"
        const val COLUMN_BRAND = "brand"
        const val COLUMN_DESC = "description"
        const val COLUMN_PRICE = "price"
        const val COLUMN_GRAMS = "grams"
        const val COLUMN_PERGRAM = "pergram"
        const val COLUMN_IMAGELINK = "imagelink"
        const val COLUMN_IMAGELINK2 = "imagelink2"
        const val COLUMN_IMAGELINK3 = "imagelink3"
        const val ID = BaseColumns._ID
    }
}
