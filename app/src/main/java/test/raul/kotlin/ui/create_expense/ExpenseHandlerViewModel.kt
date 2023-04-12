package test.raul.kotlin.ui.create_expense

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import test.raul.kotlin.database.DatabaseHelper
import test.raul.kotlin.database.entity.Category
import test.raul.kotlin.database.entity.Expense
import javax.inject.Inject


@HiltViewModel
class ExpenseHandlerViewModel @Inject constructor(private val database: DatabaseHelper): ViewModel() {

    private val _categories = MutableLiveData<List<Category>>(emptyList())
    val categories get() = _categories
    fun getCategories(){
        viewModelScope.launch {

            _categories.value =  database.categoryDao().getAll()
        }
    }

    fun createExpense(expense: Expense) : Expense {
        var id : Long = 0
        runBlocking {
            id = database.expenseDao().insert(expense)
        }
        expense.id = id
        return expense
    }
}