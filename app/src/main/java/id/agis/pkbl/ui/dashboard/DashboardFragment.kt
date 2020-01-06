package id.agis.pkbl.ui.dashboard


import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import id.agis.pkbl.R
import id.agis.pkbl.ui.dashboard.persektor.PerSektorFragment
import id.agis.pkbl.ui.dashboard.perwilayah.PerWilayahViewModel
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.backdrop.*
import kotlinx.android.synthetic.main.fragment_dashboard.*


/**
 * A simple [Fragment] subclass.
 */
class DashboardFragment : Fragment() {
    lateinit var viewModel: DashboardViewModel
    private var backdropShown = false
    private lateinit var menu: Menu
    private val listTahun = mutableListOf<String>()
    private val listBulan = mutableListOf<String>()
    private lateinit var adapterTahun: ArrayAdapter<String>
    private lateinit var adapterBulan: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterTahun = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, listTahun)
        adapterTahun.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterBulan = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, listBulan)
        adapterBulan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_tahun.adapter = adapterTahun
        sp_bulan.adapter = adapterBulan

        sp_tahun.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                viewModel.getBulan(listTahun[position])
                observeBulan()
            }
        }

        viewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)

        viewModel.getTahun()
        viewModel.tahun.observe(this, Observer {
            listTahun.clear()
            listTahun.addAll(it.map { pengajuan ->
                pengajuan.time.take(4)
            })
            adapterTahun.notifyDataSetChanged()

            viewModel.getBulan(listTahun[0])
            observeBulan()
        })

        (activity as AppCompatActivity).appbar?.elevation = 0f

        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                loadLayout()
            }

        })

        btn_tampilan.setOnClickListener {
            loadLayout()
        }
    }

    private fun loadLayout() {
        val fragment = when (tab_layout.selectedTabPosition) {
            0 -> PerSektorFragment()
            1 -> PerWilayahViewModel()
            else -> PerSektorFragment()
        }
        fragmentManager?.beginTransaction()
            ?.replace(R.id.container, fragment)
            ?.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_dashboard, menu)
        this.menu = menu
        val item = menu.findItem(R.id.action_settings)
        item.isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filter -> {
                showBackdrop()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showBackdrop() {
        val animatorSet = AnimatorSet()

        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)

        backdropShown = !backdropShown

        // Cancel the existing animations
        animatorSet.removeAllListeners()
        animatorSet.end()
        animatorSet.cancel()

        val translateY = backdrop.height

        val animator = ObjectAnimator.ofFloat(
            product_grid,
            "translationY",
            (if (backdropShown) translateY else 0).toFloat()
        )
        animator.duration = 500

        animatorSet.play(animator)
        animator.start()

        updateIcon()
    }

    private fun updateIcon() {
        if (backdropShown) {
            menu.findItem(R.id.filter).icon = context?.getDrawable(R.drawable.ic_close)
        } else {
            menu.findItem(R.id.filter).icon = context?.getDrawable(R.drawable.ic_filter)
        }
    }

    private fun convertBulan(bulan: Int): String {
        return when (bulan) {
            1 -> "Januari"
            2 -> "Februari"
            3 -> "Maret"
            4 -> "April"
            5 -> "Mei"
            6 -> "Juni"
            7 -> "Juli"
            8 -> "Agustus"
            9 -> "September"
            10 -> "Oktober"
            11 -> "November"
            12 -> "Desember"
            else -> "Januari"
        }
    }

    fun observeBulan(){
        viewModel.bulan.observe(this, Observer {
            listBulan.clear()
            listBulan.addAll(it.map { pengajuan ->
                convertBulan(pengajuan.time.substring(5, 7).toInt())
            })
            adapterBulan.notifyDataSetChanged()
            loadLayout()
        })

    }

}
