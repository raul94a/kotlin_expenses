package test.raul.kotlin.database.dao

import androidx.room.*
import test.raul.kotlin.database.entity.Expense


@Dao
interface ExpenseDao {
    @Query("Select * from expenses")
    suspend fun getAll() {}

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expense: Expense)  :Long

    @Delete
    suspend fun deleteOne(expense:Expense)

    @Update
    suspend fun updateOne(expense:Expense)
}