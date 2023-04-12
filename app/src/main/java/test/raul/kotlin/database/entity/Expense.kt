package test.raul.kotlin.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "expenses",
//    foreignKeys = [
//        ForeignKey(entity = Expense::class, parentColumns = ["category"], childColumns = ["id"])
//    ]
)
data class Expense(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long?,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "price")
    var price: Double,

    @ColumnInfo(name = "category")
    var category: Long,

    @ColumnInfo(name = "date")
    var date: Long
){
    override fun toString(): String {
        return "Expense(id=$id, description='$description', price=$price, category=$category, date=$date)"
    }
}