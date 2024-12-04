package com.example.virgo.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.virgo.model.ecommerce.Product
import com.example.virgo.model.ecommerce.ProductWithQuantity
import com.example.virgo.model.lib.Alarm
import com.example.virgo.model.lib.Reminder

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
        const val COLUMN_ALARM_HOUR = "hour"
        const val COLUMN_ALARM_MIN = "min"
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
                $COLUMN_NOTE BOOLEAN,
                $COLUMN_IS_ACTIVE BOOLEAN
            )
        """)
        db.execSQL("""
            CREATE TABLE $TABLE_ALARMS (
                $COLUMN_ALARM_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_ALARM_HOUR INTEGER,
                $COLUMN_ALARM_MIN INTEGER,
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

    fun createReminder(reminder: Reminder) {
        val db = writableDatabase

        // Insert the reminder into the reminders table
        val values = ContentValues().apply {
            put(COLUMN_NAME, reminder.name)
            put(COLUMN_DATE_CREATED, reminder.dateCreated)
            put(COLUMN_DURATION, reminder.duration)
            put(COLUMN_SKIP, reminder.skip)
            put(COLUMN_NOTE, reminder.note)
            put(COLUMN_IS_ACTIVE, reminder.isActive)
        }
        val reminderId = db.insert(TABLE_REMINDERS, null, values)

        val alarms = reminder.alarms
        val products = reminder.products
        // Insert the alarms into the alarms table
        alarms.forEach { alarmTime ->
            val alarmValues = ContentValues().apply {
                put(COLUMN_ALARM_HOUR, alarmTime.hour)
                put(COLUMN_ALARM_MIN, alarmTime.min)
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


    fun getAllReminders(db: SQLiteDatabase): List<Reminder> {
        val reminders = mutableListOf<Reminder>()

        // Query the reminders table
        val cursor = db.query(
            TABLE_REMINDERS,
            null, // Select all columns
            null, // No selection
            null, // No selection arguments
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            do {
                val reminderId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_REMINDER_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val dateCreated = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DATE_CREATED))
                val duration = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DURATION))
                val skip = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SKIP))
                val note = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NOTE)) == 1
                val isActive = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_ACTIVE)) == 1

                // Retrieve alarms for this reminder
                val alarms = getAlarmsForReminder(db, reminderId)

                // Retrieve products for this reminder
                val products = getProductsForReminder(db, reminderId)

                val reminder = Reminder(
                    name = name,
                    dateCreated = dateCreated,
                    duration = duration,
                    skip = skip,
                    note = note,
                    isActive = isActive,
                    alarms = alarms,
                    products = products
                )
                reminders.add(reminder)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return reminders
    }

    // Helper function to get alarms for a reminder
    private fun getAlarmsForReminder(db: SQLiteDatabase, reminderId: Int): List<Alarm> {
        val alarms = mutableListOf<Alarm>()

        val cursor = db.query(
            TABLE_ALARMS,
            null, // Select all columns
            "$COLUMN_REMINDER_ID_FK_ALARMS = ?", // Selection
            arrayOf(reminderId.toString()), // Selection arguments
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            do {
                val hour = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ALARM_HOUR))
                val min = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ALARM_MIN))
                alarms.add(Alarm(hour, min))
            } while (cursor.moveToNext())
        }
        cursor.close()

        return alarms
    }

    // Helper function to get products for a reminder
    private fun getProductsForReminder(db: SQLiteDatabase, reminderId: Int): List<ProductWithQuantity> {
        val products = mutableListOf<ProductWithQuantity>()

        val cursor = db.query(
            TABLE_PRODUCTS,
            null, // Select all columns
            "$COLUMN_REMINDER_ID_FK_PRODUCTS = ?", // Selection
            arrayOf(reminderId.toString()), // Selection arguments
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            do {
                val productName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_NAME))
                val productDescription = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_DESCRPT))
                val productAmount = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_AMOUNT))
                val product = Product(name = productName, description = productDescription)
                products.add(ProductWithQuantity(product = product, quantity = productAmount))
            } while (cursor.moveToNext())
        }
        cursor.close()

        return products
    }


    fun clearDatabase() {
        val db = writableDatabase
        // Delete all data from tables
        db.delete(TABLE_REMINDERS, null, null)
        db.delete(TABLE_ALARMS, null, null)
        db.delete(TABLE_PRODUCTS, null, null)
        db.close()
    }
}