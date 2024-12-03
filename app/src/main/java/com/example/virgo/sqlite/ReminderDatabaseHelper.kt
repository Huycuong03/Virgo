package com.example.virgo.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.virgo.model.ecommerce.ProductWithQuantity

class ReminderDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "reminder.db"
        private const val DATABASE_VERSION = 1

        // Reminder Table
        const val TABLE_REMINDERS = "reminders"
        const val COLUMN_REMINDER_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_DATE_CREATED = "date_created"
        const val COLUMN_DURATION = "duration"
        const val COLUMN_SKIP = "skip"
        const val COLUMN_NOTE = "note"
        const val COLUMN_IS_ACTIVE = "is_active"

        // Alarm Table
        const val TABLE_ALARMS = "alarms"
        const val COLUMN_ALARM_ID = "id"
        const val COLUMN_ALARM_TIME = "time"
        const val COLUMN_REMINDER_ID_FK_ALARMS = "reminder_id" // Renamed

        // Product Table
        const val TABLE_PRODUCTS = "products"
        const val COLUMN_PRODUCT_ID = "id"
        const val COLUMN_PRODUCT_NAME = "product_name"
        const val COLUMN_PRODUCT_DESCRPT = "product_descrpt"
        const val COLUMN_PRODUCT_AMOUNT = "amount"
        const val COLUMN_REMINDER_ID_FK_PRODUCTS = "reminder_id" // Renamed
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE $TABLE_REMINDERS (
                $COLUMN_REMINDER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_DATE_CREATED TEXT,
                $COLUMN_DURATION INTEGER,
                $COLUMN_SKIP INTEGER,
                $COLUMN_NOTE TEXT,
                $COLUMN_IS_ACTIVE INTEGER
            )
        """)
        db.execSQL("""
            CREATE TABLE $TABLE_ALARMS (
                $COLUMN_ALARM_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_ALARM_TIME TEXT NOT NULL,
                $COLUMN_REMINDER_ID_FK_ALARMS INTEGER,
                FOREIGN KEY($COLUMN_REMINDER_ID_FK_ALARMS) REFERENCES $TABLE_REMINDERS($COLUMN_REMINDER_ID) ON DELETE CASCADE
            )
        """)
        db.execSQL("""
            CREATE TABLE $TABLE_PRODUCTS (
                $COLUMN_PRODUCT_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_PRODUCT_NAME TEXT NOT NULL,
                $COLUMN_PRODUCT_DESCRPT TEXT,
                $COLUMN_PRODUCT_AMOUNT INTEGER,
                $COLUMN_REMINDER_ID_FK_PRODUCTS INTEGER,
                FOREIGN KEY($COLUMN_REMINDER_ID_FK_PRODUCTS) REFERENCES $TABLE_REMINDERS($COLUMN_REMINDER_ID) ON DELETE CASCADE
            )
        """)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCTS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ALARMS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_REMINDERS")
        onCreate(db)
    }

    fun createReminder(name: String, dateCreated: String, duration: Int, skip: Int, note: String,
                       isActive: Boolean, alarms: List<String>, products: List<ProductWithQuantity>) {
        val db = writableDatabase

        // Insert the reminder into the reminders table
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_DATE_CREATED, dateCreated)
            put(COLUMN_DURATION, duration)
            put(COLUMN_SKIP, skip)
            put(COLUMN_NOTE, note)
            put(COLUMN_IS_ACTIVE, isActive)
        }
        val reminderId = db.insert(TABLE_REMINDERS, null, values)

        // Insert the alarms into the alarms table
        alarms.forEach { alarmTime ->
            val alarmValues = ContentValues().apply {
                put(COLUMN_ALARM_TIME, alarmTime)
                put(COLUMN_REMINDER_ID_FK_ALARMS, reminderId)
            }
            db.insert(TABLE_ALARMS, null, alarmValues)
        }

        // Insert the products into the products table
        products.forEach { product ->
            val productValues = ContentValues().apply {
                put(COLUMN_PRODUCT_NAME, product.product?.name)
                put(COLUMN_PRODUCT_DESCRPT, product.product?.description)
                put(COLUMN_PRODUCT_AMOUNT, product.quantity)
                put(COLUMN_REMINDER_ID_FK_PRODUCTS, reminderId)
            }
            db.insert(TABLE_PRODUCTS, null, productValues)
        }

        db.close()
    }


    fun getActiveReminders(db: SQLiteDatabase): List<String> {
        val activeReminders = mutableListOf<String>()
        val cursor = db.query(
            TABLE_REMINDERS,
            arrayOf(COLUMN_NAME),
            "$COLUMN_IS_ACTIVE = ?",
            arrayOf("1"),
            null, null, null
        )

        // Check if the column exists by getting the index
        val nameColumnIndex = cursor.getColumnIndex(COLUMN_NAME)

        if (nameColumnIndex == -1) {
            // Handle error if the column doesn't exist
            Log.e("ReminderDatabaseHelper", "Column $COLUMN_NAME not found in the query result.")
            cursor.close()
            return emptyList() // Return an empty list if column is not found
        }

        while (cursor.moveToNext()) {
            val name = cursor.getString(nameColumnIndex) // Use the valid column index
            activeReminders.add(name)
        }
        cursor.close()
        return activeReminders
    }

    // Function to get inactive reminders
    fun getInactiveReminders(db: SQLiteDatabase): List<String> {
        val inactiveReminders = mutableListOf<String>()
        val cursor = db.query(
            TABLE_REMINDERS,
            arrayOf(COLUMN_NAME),
            "$COLUMN_IS_ACTIVE = ?",
            arrayOf("0"),
            null, null, null
        )

        // Check if the column exists by getting the index
        val nameColumnIndex = cursor.getColumnIndex(COLUMN_NAME)

        if (nameColumnIndex == -1) {
            // Handle error if the column doesn't exist
            Log.e("ReminderDatabaseHelper", "Column $COLUMN_NAME not found in the query result.")
            cursor.close()
            return emptyList() // Return an empty list if column is not found
        }

        while (cursor.moveToNext()) {
            val name = cursor.getString(nameColumnIndex) // Use the valid column index
            inactiveReminders.add(name)
        }
        cursor.close()
        return inactiveReminders
    }
}