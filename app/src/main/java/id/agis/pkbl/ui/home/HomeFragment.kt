package id.agis.pkbl.ui.home

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import id.agis.pkbl.R
import id.agis.pkbl.model.Pengajuan
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import id.agis.pkbl.constant.Constant
import id.agis.pkbl.util.toTitleCase
import kotlinx.android.synthetic.main.fragment_home.tv_email
import kotlinx.android.synthetic.main.nav_header_main.view.*
import pub.devrel.easypermissions.EasyPermissions
import id.agis.pkbl.BuildConfig

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var viewModel: HomeViewModel
    lateinit var adapter: HomeAdapter
    val listPengajuan = mutableListOf<Pengajuan>()
    private lateinit var menu: Menu

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        val sharedPreferences = context!!.getSharedPreferences(Constant.USER, Context.MODE_PRIVATE)
        val name = sharedPreferences.getString(Constant.USER_NAME, "").toTitleCase()
        val user = sharedPreferences.getString(Constant.USER_USERNAME, "")
        val role = sharedPreferences.getString(Constant.USER_ROLE, "")
        val email = sharedPreferences.getString(Constant.USER_EMAIL, "")
        val image = sharedPreferences.getString(Constant.USER_IMAGE, "")

        if (!checkPermission()) {
            requestPermissions()
        }

        val indexOfSpace = name!!.indexOf(' ')
        val firstName = if(indexOfSpace > 0){
            name.take(indexOfSpace)
        }else {
            name
        }

        tv_name.text = resources.getString(R.string.hello, firstName)
        tv__home_username.text = user
        tv_role.text = role
        tv_email.text = email
        Glide.with(this).load(BuildConfig.BASE_URL + image).into(iv_profile)

        adapter = HomeAdapter(listPengajuan)
        rv_file.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_file.adapter = adapter

        tv_view_all.setOnClickListener {
            val action = HomeFragmentDirections.actionNavHomeToNavListPemohon("Uncompleted Job",
                listPengajuan.toTypedArray(),
                "home"
            )
            findNavController().navigate(action)
        }

        btn_dashboard.setOnClickListener {
            startFragment(R.id.nav_dashboard)
        }
        btn_pending_job.setOnClickListener {
            startFragment(R.id.nav_pending_job)
        }
        btn_document.setOnClickListener {
            startFragment(R.id.nav_document)
        }
        btn_info.setOnClickListener {
            startFragment(R.id.nav_info)
        }

        viewModel.getPenugasan()
        viewModel.listPengajuan.observe(this, Observer {
            listPengajuan.clear()
            when (role) {
                "Surveyor" -> {
                    listPengajuan.addAll(it.filter { it.status == "Penugasan" && it.surveyor == user })
                }
                "Penilai" -> listPengajuan.addAll(it.filter { it.status == "Penilaian" })
                "Penyetuju" -> listPengajuan.addAll(it.filter { it.status == "Persetujuan" })
                "Bendahara" -> listPengajuan.addAll(it.filter { it.status == "Pencairan" })
            }
            if(listPengajuan.size < 1){
                tv_view_all.visibility = View.INVISIBLE
                tv_uncompleted_job.visibility = View.INVISIBLE
            }else{
                tv_view_all.visibility = View.VISIBLE
                tv_uncompleted_job.visibility = View.VISIBLE
            }
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
                val action = HomeFragmentDirections.actionNavHomeToNavSearch()
                findNavController().navigate(action)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun checkPermission(): Boolean {
        return EasyPermissions.hasPermissions(context!!, Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun requestPermissions() {
        val rationale = "This application need your permission to access directory."
        EasyPermissions.requestPermissions(
            activity as Activity,
            rationale,
            991,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }
}