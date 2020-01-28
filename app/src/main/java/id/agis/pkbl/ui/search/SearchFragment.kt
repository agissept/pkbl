package id.agis.pkbl.ui.search

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import id.agis.pkbl.R
import id.agis.pkbl.model.Pengajuan
import kotlinx.android.synthetic.main.fragment_search.*
import org.jetbrains.anko.support.v4.toast

class SearchFragment : Fragment() {

    private val listTahun = mutableListOf<String>()
    private val listBulan = mutableListOf<String>()
    private lateinit var adapterTahun: ArrayAdapter<String>
    private lateinit var adapterBulan: ArrayAdapter<String>

    lateinit var searchView: SearchView
    lateinit var viewModel: SearchViewModel
    lateinit var adapter: SearchAdapter
    val listPengajuan = mutableListOf<Pengajuan>()
    lateinit var owner: Fragment
    var keyword = ""
    var bulan = ""
    var tahun = ""
    var mQuery = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SearchAdapter(listPengajuan)
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        rv_file.adapter = adapter
        rv_file.layoutManager = LinearLayoutManager(context)

        viewModel.getTahun()
        viewModel.tahun.observe(this, Observer {
            listTahun.clear()
            listBulan.add("All")
            listTahun.addAll(it.map { pengajuan ->
                pengajuan.time.take(4)
            })
            adapterTahun.notifyDataSetChanged()

            viewModel.getBulan(listTahun[0])
            observeBulan()
        })

        owner = this

        adapterTahun = ArrayAdapter(context!!, R.layout.spinner_item, listTahun)
        adapterTahun.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterBulan = ArrayAdapter(context!!, R.layout.spinner_item, listBulan)
        adapterBulan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_tahun.adapter = adapterTahun
        sp_bulan.adapter = adapterBulan

        sp_tahun.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                viewModel.getBulan(listTahun[position])
                observeBulan()
                tahun = listTahun[position]
                viewModel.getSearch(keyword, bulan, tahun)
            }
        }

        sp_bulan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                viewModel.getSearch(keyword, bulan, tahun)
                observeResult()
                bulan = listTahun[position]
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)

        val item = menu.findItem(R.id.action_settings)
        item.isVisible = false

        val searchItem = menu.findItem(R.id.search_bar)
        searchView = searchItem?.actionView as SearchView
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.queryHint = "Search"

        val typeFace = ResourcesCompat.getFont(context!!, R.font.rubik)
        val textView = searchView.findViewById<TextView>(androidx.appcompat.R.id.search_src_text)
        textView.typeface = typeFace

        (searchView.findViewById(androidx.appcompat.R.id.search_close_btn) as ImageView).setImageResource(
            R.drawable.ic_close_black_24dp
        )
        searchItem.expandActionView()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query == null){
                    tv_error.visibility = View.INVISIBLE
                }
                mQuery = query ?: ""
                progress_bar.visibility = View.VISIBLE
                query?.let {
                    viewModel.getSearch(it, bulan, tahun)
                    observeResult()
                    keyword = it
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if(query == null){
                    tv_error.visibility = View.INVISIBLE
                }
                mQuery = query ?: ""

                progress_bar?.visibility = View.VISIBLE
                query?.let {
                    viewModel.getSearch(it, bulan, tahun)
                    observeResult()
                    keyword = it
                }
                return false
            }
        })

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                activity!!.onBackPressed()
                return false
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    fun observeResult() {
        viewModel.listPengajuan.observe(owner, Observer {
            listPengajuan.clear()
            listPengajuan.addAll(it)
            adapter.notifyDataSetChanged()
            if (listPengajuan.size > 0 ) {
                tv_error.visibility = View.INVISIBLE
            } else {
                tv_error.visibility = View.VISIBLE
            }
            if(mQuery == ""){
                tv_error.visibility = View.INVISIBLE
            }
            progress_bar.visibility = View.INVISIBLE
        })
    }

    fun observeBulan() {
        viewModel.bulan.observe(this, Observer {
            listBulan.add("All")
            listBulan.clear()
            listBulan.addAll(it.map { pengajuan ->
                convertBulan(pengajuan.time.substring(5, 7).toInt())
            })
            adapterBulan.notifyDataSetChanged()
            viewModel.getSearch(keyword, bulan, tahun)
            observeResult()
        })

    }

    private fun convertBulan(bulan: Int): String {
        return when (bulan) {
            1 -> "Januari"
            2 -> "Februari"
            3 -> "Maret"
            4 -> "April"
            5 -> "Mei"
            6 -> "Juni"
            7 -> "Juli"
            8 -> "Agustus"
            9 -> "September"
            10 -> "Oktober"
            11 -> "November"
            12 -> "Desember"
            else -> "Januari"
        }
    }


}
