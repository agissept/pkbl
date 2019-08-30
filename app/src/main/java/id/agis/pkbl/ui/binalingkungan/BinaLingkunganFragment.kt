package id.agis.pkbl.ui.binalingkungan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import id.agis.pkbl.R
import id.agis.pkbl.model.BinaLingkugan
import kotlinx.android.synthetic.main.fragment_bina_lingkungan.*

class BinaLingkunganFragment : Fragment() {

    private val listBinaLingkungan = mutableListOf<BinaLingkugan>()
    private lateinit var adapter: BinaLingkunganAdapter
    private lateinit var viewModel: BinaLingkunganViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bina_lingkungan, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(BinaLingkunganViewModel::class.java)
        viewModel.data.observe(this, Observer {
            listBinaLingkungan.clear()
            listBinaLingkungan.addAll(it)
            adapter.notifyDataSetChanged()
            progress_circular.visibility = View.INVISIBLE
        })


        adapter = BinaLingkunganAdapter(listBinaLingkungan)
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter =adapter
    }

}
