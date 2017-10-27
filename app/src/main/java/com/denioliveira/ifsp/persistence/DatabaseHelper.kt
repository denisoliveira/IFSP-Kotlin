package com.denioliveira.ifsp.persistence

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.denioliveira.ifsp.domain.Book
import org.jetbrains.anko.db.*

class DatabaseHelper(context: Context) : ManagedSQLiteOpenHelper(context, "Database", null, 1) {
    companion object {
        private var instance: DatabaseHelper? = null

        @Synchronized
        fun Instance(context: Context): DatabaseHelper {
            if (instance == null) {
                instance = DatabaseHelper(context.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(database: SQLiteDatabase?) {
        database?.createTable(
                Book.TABLE_NAME, true,
                Book.COLUMN_ID to INTEGER + PRIMARY_KEY,
                Book.COLUMN_NAME to TEXT,
                Book.COLUMN_ISBN to INTEGER,
                Book.COLUMN_AUTHOR to TEXT,
                Book.COLUMN_STYLE to TEXT
                )
    }

    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        database?.dropTable(Book.TABLE_NAME, true)
    }
}