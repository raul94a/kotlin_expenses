package test.raul.kotlin.ui.create_expense

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
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

    private lateinit var _dateSelectorImageView: ImageView
    private lateinit var _tvDate: TextView

    private lateinit var _categoriesDropdown: Spinner
    private lateinit var arrayAdapter: CategoriesSpinnerAdapter
    private lateinit var _etPrice: EditText
    private lateinit var _etDescription: EditText
    private  var _expense: Expense? = null
    private lateinit var _loadingLayout: ConstraintLayout
    private lateinit var _successLayout: ConstraintLayout
    private lateinit var _successArrow: ImageView
    private lateinit var _mainView: View
    private lateinit var _successView: View
    private lateinit var _loadingView: View

    private lateinit var vm: ExpenseHandlerViewModel
    private var _category: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)


//         _successLayout = inflater.inflate(R.layout.included_layout2, rootView, false)
//         _loadingLayout= inflater.inflate(R.layout.included_layout3, rootView, false)
        bindUI()


        onClickDateSelectorIcon()

        //observe
        vm = ViewModelProvider(requireActivity()).get(ExpenseHandlerViewModel::class.java)
        vm.getCategories()

        observeVM()

        onSelectCategory()

        return binding.root

    }

    private fun onSelectCategory() {
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
    }

    fun observeVM() {
        vm.expense.observe(viewLifecycleOwner){
            _expense  = it
        }
        vm.categories.observe(viewLifecycleOwner) {
            arrayAdapter = CategoriesSpinnerAdapter(requireContext(), it)

            _categoriesDropdown.adapter = arrayAdapter
        }
        vm.status.observe(viewLifecycleOwner) {
            Log.e("Status changed to", "$it")
            when (it) {
                ExpenseHandlerStatus.HANDLE -> {
                    _mainView.visibility = View.VISIBLE
                    _loadingView.visibility = View.GONE
                    _successView.visibility = View.GONE
                }
                ExpenseHandlerStatus.LOADING -> {
                    _mainView.visibility = View.GONE
                    _loadingView.visibility = View.VISIBLE
                    _successView.visibility = View.GONE
                }
                else -> {
                    val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.bounce)
                    animation.repeatCount = Animation.INFINITE
                    _successArrow.startAnimation(animation)
                    _mainView.visibility = View.GONE
                    _loadingView.visibility = View.GONE
                    _successView.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.findViewById<Button>(R.id.button_first).setOnClickListener {
            vm.changeStatus(ExpenseHandlerStatus.LOADING)
            createExpense()
        }
        binding.root.findViewById<Button>(R.id.btnContinue).setOnClickListener {

            Log.e("Returning to activity", "")
            val ouput = Intent()
            ouput.putExtra("expense", Gson().toJson(_expense))

            Log.e("Returning to activity", ouput.getStringExtra("back") ?: "null")

            requireActivity().setResult(99, ouput)
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindUI() {
        val view = binding.root
        _mainView = view.findViewById(R.id.main_content)
        _successView = view.findViewById(R.id.success_layout)
        _loadingView = view.findViewById(R.id.loading_content)
        _dateSelectorImageView = view.findViewById(R.id.ivDate)
        _tvDate = view.findViewById(R.id.tvDate)
        _categoriesDropdown = view.findViewById(R.id.dropdownCategories)
        _etPrice = view.findViewById(R.id.etPrice)
        _etDescription = view.findViewById(R.id.etDescription)


        // _loadingLayout = _loadingView.findViewById(R.id.createExpenseLoadingLayout)
        _successArrow = _successView.findViewById(R.id.ivArrow)
    }

    //expense methods

    private fun createExpense() {


        val vm = ViewModelProvider(requireActivity()).get(ExpenseHandlerViewModel::class.java)
        val date: LocalDate = DateFormatter.toDate(_tvDate.text.toString())
        val price: Double = _etPrice.text.toString().toDouble()
        val description = _etDescription.text.toString()
        val category = _category
        Log.e("CATEGORY", "$_category")

        val expense = Expense(
            id = null,
            date = date.toEpochDay() * 24 * 60 * 60 * 1000,
            price = price,
            description = description,
            category = category
        )
        _expense = expense
        vm.createExpense(expense)

    }


    //date selector methods

    private fun onClickDateSelectorIcon() {
        _dateSelectorImageView.setOnClickListener {
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