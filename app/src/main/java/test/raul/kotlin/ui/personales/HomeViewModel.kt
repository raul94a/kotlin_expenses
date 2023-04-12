package test.raul.kotlin.ui.personales


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import test.raul.kotlin.database.DatabaseHelper
import test.raul.kotlin.database.entity.Expense
import javax.inject.Inject


@HiltViewModel
class HomeViewModel

@Inject constructor(private val database: DatabaseHelper)

    : ViewModel() {



    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text


    fun changeText(text: String){
        _text.value = text
    }


    private val _strings = MutableLiveData<MutableList<String>>(mutableListOf())
    val strings: MutableLiveData<MutableList<String>> = _strings


    fun addString(str: String) {
        var list = this.strings.value
        list!!.add(str)
        this._strings.value = list;
    }

    private val _expenses
    = MutableLiveData<List<Expense>>(emptyList())

    val expenses get() =_expenses

    fun addExpense(expense: Expense){
        val expenses : MutableList<Expense> = this.expenses.value!!.toMutableList()
        expenses.add(expense)
        _expenses.value = expenses
    }





}