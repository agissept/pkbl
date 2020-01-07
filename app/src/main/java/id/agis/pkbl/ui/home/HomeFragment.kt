package id.agis.pkbl.ui.home

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.agis.pkbl.R
import id.agis.pkbl.model.Pengajuan
import id.agis.pkbl.ui.search.SearchActivity
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.startActivity
import id.agis.pkbl.constant.Constant
import  id.agis.pkbl.viewmodel.ViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    lateinit var adapter: HomeAdapter
    val listPengajuan = mutableListOf<Pengajuan>()
    private lateinit var menu: Menu

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
           ViewModelFactory.getInstance().create(HomeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).appbar?.elevation = 0f

        val sharedPreferences = context!!.getSharedPreferences(Constant.USER, Context.MODE_PRIVATE)
        val user = sharedPreferences.getString(Constant.USER_NAME, "")
        val role = sharedPreferences.getString(Constant.USER_ROLE, "")
        val email = sharedPreferences.getString(Constant.USER_EMAIL, "")

        tv_title_name.text = resources.getString(R.string.hello, user)
        tv_name.text = user
        tv_role.text = role
        tv_email.text = email

        adapter = HomeAdapter(listPengajuan)
        recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recycler_view.adapter = adapter



        btn_dashboard.setOnClickListener {
            startFragment(R.id.nav_dashboard)
        }
        btn_pending_job.setOnClickListener {
            startFragment(R.id.nav_pending_job)
        }
        btn_dokumen.setOnClickListener {
            startFragment(R.id.nav_dokumen)
        }
        btn_info.setOnClickListener {
            startFragment(R.id.nav_info)
        }

        viewModel.getPenugasan()
        viewModel.listPengajuan.observe(this, Observer {
            listPengajuan.clear()
            when (role) {
                "surveyor" ->{
                    listPengajuan.addAll(it.filter { it.status == "Penugasan" && it.surveyor == user })
                }
                "penilai" -> listPengajuan.addAll(it.filter { it.status == "Penilaian" })
                "penyetuju" -> listPengajuan.addAll(it.filter { it.status == "Persetujuan" })
                "bendahara" -> listPengajuan.addAll(it.filter { it.status == "Pencairan" })
            }
            println("aaaaaaaaaaaaaaaaaaaa ${listPengajuan.size}" )
            adapter.notifyDataSetChanged()
        })
    }

    private fun startFragment(id: Int) {
        findNavController().navigate(id)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
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
            R.id.search -> {
                startActivity<SearchActivity>()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}