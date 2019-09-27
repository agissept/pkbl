package id.agis.pkbl.ui.binalingkungan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import id.agis.pkbl.R
import id.agis.pkbl.model.BinaLingkugan
import kotlinx.android.synthetic.main.fragment_bina_lingkungan.*

class BinaLingkunganFragment : Fragment() {

    private val listBinaLingkungan = mutableListOf<BinaLingkugan>()
    private lateinit var adapter: BinaLingkunganAdapter
//    private lateinit var viewModel: BinaLingkunganViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bina_lingkungan, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = BinaLingkunganAdapter(listBinaLingkungan)
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter =adapter

        //-------start-----///
        //dummy data

        val dummyData =  BinaLingkugan(1, 1, "Lorem Ipsum", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin lorem mi, eleifend sed tristique quis, vestibulum consectetur dolor. In hac habitasse platea dictumst. Nam rhoncus sollicitudin ullamcorper. Aenean consectetur, urna molestie pharetra eleifend, lectus est pharetra ipsum, id luctus risus turpis et ante.")


        listBinaLingkungan.clear()
        for (x in 0 until 15){
            listBinaLingkungan.add(dummyData)

        }

        listBinaLingkungan.addAll(listBinaLingkungan)
        adapter.notifyDataSetChanged()

        //-------end-----///

//        viewModel = ViewModelProviders.of(this).get(BinaLingkunganViewModel::class.java)
//        viewModel.data.observe(this, Observer {
//            listBinaLingkungan.clear()
//            listBinaLingkungan.addAll(it)
//            adapter.notifyDataSetChanged()
            progress_circular.visibility = View.INVISIBLE
//        })

    }

}
