package test.raul.kotlin.database.entity

import android.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "r")
    var r: Int,

    @ColumnInfo(name = "g")
    var g: Int,
    @ColumnInfo(name = "b")
    var b: Int,

    @ColumnInfo(name = "a")
    var a: Int,


) {
    fun getColor() : Int{
        return Color.argb(a,r,g,b)
    }
    override fun toString(): String {
        return "Category(id=$id, description='$name', r=$r, g=$g, b=$b, a=$a)"
    }
}