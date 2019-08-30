package id.agis.pkbl.ui.kemitraan


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import id.agis.pkbl.R
import id.agis.pkbl.model.Kemitraan
import kotlinx.android.synthetic.main.fragment_bina_lingkungan.*

class KemitraanFragment : Fragment() {

    private val listKemitraan = mutableListOf<Kemitraan>()
    private lateinit var adapter: KemitraanAdapter
    private lateinit var viewModel: KemitraanViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kemitraan, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(KemitraanViewModel::class.java)
        viewModel.data.observe(this, Observer {
            listKemitraan.clear()
            listKemitraan.addAll(it)
            adapter.notifyDataSetChanged()
            progress_circular.visibility = View.INVISIBLE
        })

        adapter = KemitraanAdapter(listKemitraan)
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter =adapter
    }
}
