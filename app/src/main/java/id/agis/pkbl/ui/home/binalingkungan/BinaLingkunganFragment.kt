package id.agis.pkbl.ui.home.binalingkungan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.agis.pkbl.R
import id.agis.pkbl.data.local.entities.PemohonEntity
import id.agis.pkbl.ui.pendingjob.listpemohon.ListPengajuanAdapter

class BinaLingkunganFragment : Fragment() {

    private val listBinaLingkungan = mutableListOf<PemohonEntity>()
    private lateinit var adapter: ListPengajuanAdapter
    private lateinit var viewModel: BinaLingkunganViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        adapter = ListPemohonAdapter(listBinaLingkungan)
//        recycler_view.layoutManager = LinearLayoutManager(context)
//        recycler_view.adapter = adapter
//
//        viewModel =
//            ViewModelFactory.getInstance(view.context).create(BinaLingkunganViewModel::class.java)
//
//        getData()
//        swipe_refresh.setOnRefreshListener {
//            getData()
//            progress_circular.visibility = View.GONE
//        }

    }

//    private fun getData() {
//        viewModel.data.observe(this, Observer {
//            when (it.status) {
//                Status.SUCCESS -> {
//                    listBinaLingkungan.clear()
//                    listBinaLingkungan.addAll(it.data!!)
//                    adapter.notifyDataSetChanged()
//                    progress_circular.visibility = View.GONE
//                    swipe_refresh.isRefreshing = false
//                }
//                Status.ERROR -> {
//                }
//                Status.LOADING -> {
//                    progress_circular.visibility = View.VISIBLE
//                }
//            }
//
//        })
//    }

}
