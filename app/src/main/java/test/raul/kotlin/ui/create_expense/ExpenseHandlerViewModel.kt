package test.raul.kotlin.ui.create_expense

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import test.raul.kotlin.database.DatabaseHelper
import test.raul.kotlin.database.entity.Category
import test.raul.kotlin.database.entity.Expense
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class ExpenseHandlerViewModel @Inject constructor(private val database: DatabaseHelper): ViewModel() {


    private val _status = MutableLiveData(ExpenseHandlerStatus.HANDLE)
    val status get() = _status
    fun changeStatus(status: ExpenseHandlerStatus){
        _status.value = status
    }


    private val _categories = MutableLiveData<List<Category>>(emptyList())
    val categories get() = _categories
    fun getCategories(){
        viewModelScope.launch {

            _categories.value =  database.categoryDao().getAll()
        }
    }

    val expense = MutableLiveData<Expense?>(null)
    fun setExpense(expense: Expense){
        this.expense.value = expense
    }

    fun createExpense(expense: Expense){
        var id : Long = 0

        viewModelScope.launch {
            delay(8000)
            id = database.expenseDao().insert(expense)
            expense.id = id
            setExpense(expense)
            changeStatus(ExpenseHandlerStatus.CONTINUE)

        }

    }
}

enum class ExpenseHandlerStatus {
    HANDLE, LOADING, CONTINUE
}