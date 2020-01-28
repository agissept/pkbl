package id.agis.pkbl.ui.info


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import id.agis.pkbl.R
import id.agis.pkbl.model.Info
import kotlinx.android.synthetic.main.fragment_info.*

/**
 * A simple [Fragment] subclass.
 */
class InfoFragment : Fragment(R.layout.fragment_info) {


    lateinit var viewModel: InfoViewModel
    lateinit var adapter: InfoAdapter
    private val listInfo = mutableListOf<Info>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(InfoViewModel::class.java)

        val adapter = InfoAdapter(listInfo)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(context!!)

        viewModel.getInfo()
        viewModel.listInfo.observe(this, Observer {
            listInfo.clear()
            listInfo.addAll(it)
            adapter.notifyDataSetChanged()
        })
    }


}
