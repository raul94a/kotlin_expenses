package test.raul.kotlin.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import test.raul.kotlin.database.dao.CategoryDao
import test.raul.kotlin.database.dao.ExpenseDao
import test.raul.kotlin.database.entity.*

@Database(
    exportSchema = true,
    entities = [Expense::class , Category::class],
    version = 1)

abstract class DatabaseHelper : RoomDatabase() {
    abstract  fun expenseDao(): ExpenseDao
    abstract  fun categoryDao(): CategoryDao

    companion object {
        @Volatile private var instance: DatabaseHelper? = null


        fun getInstance(context: Context): DatabaseHelper {
            return instance?: synchronized(this){
                instance?: buildDatabase(context).also { instance = it}
            }
        }

        private fun buildDatabase(context: Context): DatabaseHelper {
            return Room.databaseBuilder(context, DatabaseHelper::class.java, "db")
                .addCallback(
                    object : Callback() {
                    }
                )
                .build()
        }

    }
}