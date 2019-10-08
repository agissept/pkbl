package id.agis.pkbl.ui.home.kemitraan


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import id.agis.pkbl.R
import id.agis.pkbl.model.Pemohon
import id.agis.pkbl.ui.home.HomeAdapter
import id.agis.pkbl.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_bina_lingkungan.*

class KemitraanFragment : Fragment() {

    private val listKemitraan = mutableListOf<Pemohon>()
    private lateinit var adapter: HomeAdapter
    private lateinit var viewModel: KemitraanViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kemitraan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HomeAdapter(listKemitraan)
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter =adapter

        viewModel = ViewModelFactory.getInstance(view.context).create(KemitraanViewModel::class.java)
        viewModel.data.observe(this, Observer {
            listKemitraan.clear()
            listKemitraan.addAll(it)
            adapter.notifyDataSetChanged()
            progress_circular.visibility = View.INVISIBLE
        })
    }
}
