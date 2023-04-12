package test.raul.kotlin.ui.create_expense

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CursorAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import test.raul.kotlin.R
import test.raul.kotlin.database.entity.Category
import test.raul.kotlin.database.entity.Expense
import test.raul.kotlin.databinding.FragmentFirstBinding
import test.raul.kotlin.ui.adapters.CategoriesSpinnerAdapter
import test.raul.kotlin.ui.pickers.DatePickerFragment
import test.raul.kotlin.utils.DateFormatter
import java.time.Instant
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private lateinit var _dateSelectorImageView : ImageView
    private lateinit var _tvDate: TextView
    private lateinit var  _categoriesDropdown : Spinner
    private lateinit var arrayAdapter: CategoriesSpinnerAdapter
    private lateinit var _etPrice: EditText
    private lateinit var _etDescription : EditText
    private lateinit var _expense: Expense


    private var _category: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        bindUI()



        onClickDateSelectorIcon()

        //observe

        val vm = ViewModelProvider(requireActivity()).get(ExpenseHandlerViewModel::class.java)
        vm.getCategories()
        vm.categories.observe(viewLifecycleOwner) {
            arrayAdapter = CategoriesSpinnerAdapter(requireContext(),it)

            _categoriesDropdown.adapter = arrayAdapter
        }


        _categoriesDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedName = arrayAdapter.getItem(p2)
           Log.e("SELECTED", "${selectedName?.id}")
                _category = selectedName!!.id
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            createExpense()
//            Log.e("Returning to activity", "")
//            val ouput = Intent()
//            ouput.putExtra("expense",Gson().toJson(_expense))
//
//            Log.e("Returning to activity", ouput.getStringExtra("back") ?: "null")
//
//            requireActivity().setResult(99,ouput)
//            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindUI(){
        val view  = binding.root
        _dateSelectorImageView = view.findViewById(R.id.ivDate)
        _tvDate = view.findViewById(R.id.tvDate)
        _categoriesDropdown = view.findViewById(R.id.dropdownCategories)
        _etPrice = view.findViewById<EditText>(R.id.etPrice)
        _etDescription = view.findViewById<EditText>(R.id.etDescription)
    }

    //expense methods

    fun createExpense(){


        val vm = ViewModelProvider(requireActivity()).get(ExpenseHandlerViewModel::class.java)

        val date :LocalDate = DateFormatter.toDate(_tvDate.text.toString())
        val price : Double = _etPrice.text.toString().toDouble()
        val description = _etDescription.text.toString()
        val category = _category
        Log.e("CATEGORY", "$_category")

        val expense = Expense(id = null ,date = date.toEpochDay() * 24 * 60 *60 *1000, price = price, description = description, category = category)
        _expense = expense
        vm.createExpense(expense)

    }



    //date selector methods

    private fun onClickDateSelectorIcon(){
        _dateSelectorImageView.setOnClickListener{
            showDatePicker()
        }
    }

    @SuppressLint("SetTextI18n")
    fun showDatePicker() {
        val datePicker = DatePickerFragment { _, year, month, day ->
            val mMonth = DateFormatter.getCorrectMonthFormat(month)
            val mDay = DateFormatter.getCorrectDayFormat(day)
            _tvDate.text = "$mDay/$mMonth/$year"

        }
        datePicker.show(requireActivity().supportFragmentManager, "datePicker")
    }





}