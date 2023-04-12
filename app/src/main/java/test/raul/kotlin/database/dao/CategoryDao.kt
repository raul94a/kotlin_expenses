package test.raul.kotlin.database.dao

import androidx.room.*
import test.raul.kotlin.database.entity.Category
import test.raul.kotlin.database.entity.Expense

@Dao
interface CategoryDao {
    @Query("Select * from categories")
    suspend fun getAll() : List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category)

    @Delete
    suspend fun deleteOne(category: Category)

    @Update
    suspend fun updateOne(category: Category)
}