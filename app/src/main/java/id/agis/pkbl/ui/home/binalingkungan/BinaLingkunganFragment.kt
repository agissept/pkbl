package id.agis.pkbl.ui.home.binalingkungan

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
import id.agis.pkbl.ui.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_bina_lingkungan.*

class BinaLingkunganFragment : Fragment() {

    private val listBinaLingkungan = mutableListOf<Pemohon>()
    private lateinit var adapter: HomeAdapter
    private lateinit var viewModel: BinaLingkunganViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bina_lingkungan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HomeAdapter(listBinaLingkungan)
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter =adapter

        viewModel = ViewModelFactory.getInstance(view.context).create(BinaLingkunganViewModel::class.java)
        viewModel.data.observe(this, Observer {
            listBinaLingkungan.clear()
            listBinaLingkungan.addAll(it)
            adapter.notifyDataSetChanged()
            progress_circular.visibility = View.INVISIBLE
        })

    }

}
