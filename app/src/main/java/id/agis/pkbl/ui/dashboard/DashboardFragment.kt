package id.agis.pkbl.ui.dashboard


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import id.agis.pkbl.R
import id.agis.pkbl.ui.dashboard.angsuran.AngsuranFragment
import id.agis.pkbl.ui.dashboard.perbulan.PerBulanFragment
import id.agis.pkbl.ui.dashboard.persektor.PerSektorFragment
import id.agis.pkbl.ui.dashboard.perwilayah.perpropinsi.PerPropinsiFragment
import kotlinx.android.synthetic.main.fragment_dashboard.*

/**
 * A simple [Fragment] subclass.
 */
class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadLayout(PerSektorFragment())

        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab_layout.selectedTabPosition) {
                    0 -> {
                        loadLayout(PerSektorFragment())
                    }
                    1 -> {
                        loadLayout(PerPropinsiFragment())
                    }
                    2 -> {
                        loadLayout(PerBulanFragment())
                    }
                    3 -> {
                        loadLayout(AngsuranFragment())
                    }
                }
            }

        })
    }

    private fun loadLayout(fragment: Fragment) {
        fragmentManager?.beginTransaction()
            ?.replace(R.id.container, fragment)
            ?.commit()
    }
}
