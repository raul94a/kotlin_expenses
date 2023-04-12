package test.raul.kotlin.ui.personales

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import test.raul.kotlin.databinding.FragmentHomeBinding
import test.raul.kotlin.ui.create_expense.CreateExpenseActivity

class HomeFragment : Fragment() {

private var _binding: FragmentHomeBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!
  private var _navController: NavHostController? = null
  private lateinit var homeViewModel : HomeViewModel
  private lateinit var startForResult : ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == 99){
                Log.e("Data", "${it.data}")
                val str = it.data?.getStringExtra("back")
                Log.e("BACK", "$str")
                homeViewModel.addString(str ?: "es nula")
            }
        }
    }
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
   homeViewModel =
            ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

    homeViewModel.changeText("Texto actualizado desde el fragmento home");

    _navController = NavHostController(requireContext())



    _binding = FragmentHomeBinding.inflate(inflater, container, false)
    val root: View = binding.root

    binding.fab.setOnClickListener {

      val intent: Intent = Intent(requireActivity(), CreateExpenseActivity::class.java)
        startForResult.launch(intent)

    }

    val textView: TextView = binding.textHome
    homeViewModel.text.observe(viewLifecycleOwner) {
      textView.text = it
    }
    homeViewModel.strings.observe(viewLifecycleOwner) { at ->
      val strs = at.size
      textView.text = "El numero de strings es: $strs"
    }
    return root
  }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}

