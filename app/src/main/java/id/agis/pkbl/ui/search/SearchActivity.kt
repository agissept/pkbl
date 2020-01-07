package id.agis.pkbl.ui.search

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import id.agis.pkbl.R
import id.agis.pkbl.model.Pengajuan
import id.agis.pkbl.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    lateinit var searchView: SearchView
    lateinit var viewModel: SearchViewModel
    lateinit var adapter: SearchAdapter
    val listPengajuan = mutableListOf<Pengajuan>()
    lateinit var owner: AppCompatActivity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar)

        adapter = SearchAdapter(listPengajuan)
        viewModel = ViewModelFactory.getInstance().create(SearchViewModel::class.java)

        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)

         owner = this

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu?.findItem(R.id.search_bar)
        searchView = searchItem?.actionView as SearchView
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.queryHint = "Search"
        (searchView.findViewById(androidx.appcompat.R.id.search_close_btn) as ImageView).setImageResource(R.drawable.ic_close_black_24dp)
        searchItem.expandActionView()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.getSearch(it)
                    viewModel.listPengajuan.observe(owner, Observer {
                        listPengajuan.clear()
                        listPengajuan.addAll(it)
                        adapter.notifyDataSetChanged()
                        println(listPengajuan.size.toString() + " aaaaaaaaaaa")
                    })}
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                query?.let { viewModel.getSearch(it)
                    viewModel.listPengajuan.observe(owner, Observer {
                        listPengajuan.clear()
                        listPengajuan.addAll(it)
                        adapter.notifyDataSetChanged()
                        println(listPengajuan.size.toString() + " aaaaaaaaaaa")
                    })}
                return false
            }

        })

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                onBackPressed()
                return false
            }
        })

        return true
    }

}
