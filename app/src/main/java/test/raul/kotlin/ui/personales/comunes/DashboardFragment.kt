package test.raul.kotlin.ui.personales.comunes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import test.raul.kotlin.R
import test.raul.kotlin.databinding.FragmentDashboardBinding
import test.raul.kotlin.ui.personales.HomeViewModel

class DashboardFragment : Fragment() {

private var _binding: FragmentDashboardBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!
    private var _navController: NavController? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
      val homeViewModel =
          ViewModelProvider(this).get(HomeViewModel::class.java)
      _navController = requireActivity().findNavController(R.id.nav_host_fragment_activity_main)

      _binding = FragmentDashboardBinding.inflate(inflater, container, false)
    val root: View = binding.root
    val fab: Unit = binding.fab.setOnClickListener{
        //launch activity


    }
    val textView: TextView = binding.textDashboard
    homeViewModel.text.observe(viewLifecycleOwner) {
      textView.text = it
    }
    return root
  }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}