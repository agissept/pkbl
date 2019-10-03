package id.agis.pkbl.ui.kemitraan


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

        adapter = KemitraanAdapter(listKemitraan)
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter =adapter

//        //-------start-----///
//        //dummy
//        val dummyData =  Kemitraan(1, 1, "Lorem Ipsum", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin lorem mi, eleifend sed tristique quis, vestibulum consectetur dolor. In hac habitasse platea dictumst. Nam rhoncus sollicitudin ullamcorper. Aenean consectetur, urna molestie pharetra eleifend, lectus est pharetra ipsum, id luctus risus turpis et ante.")
//
//
//        listKemitraan.clear()
//        for (x in 0 until 15){
//            listKemitraan.add(dummyData)
//        }
//
//        adapter.notifyDataSetChanged()

        //-------end-----///

        viewModel = ViewModelProviders.of(this).get(KemitraanViewModel::class.java)
        viewModel.data.observe(this, Observer {
            listKemitraan.clear()
            listKemitraan.addAll(it)
            adapter.notifyDataSetChanged()
            progress_circular.visibility = View.INVISIBLE
        })




    }
}
