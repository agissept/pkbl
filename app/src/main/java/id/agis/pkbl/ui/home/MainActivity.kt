package id.agis.pkbl.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.agis.pkbl.R
import id.agis.pkbl.ui.binalingkungan.BinaLingkunganFragment
import id.agis.pkbl.ui.kemitraan.KemitraanFragment
import id.agis.pkbl.util.TabAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = TabAdapter(supportFragmentManager)
        adapter.addFragment(BinaLingkunganFragment(), "Bina Lingkungan")
        adapter.addFragment(KemitraanFragment(), "Kemitraan")
        tab_layout.setupWithViewPager(view_pager)
        view_pager.adapter = adapter
    }
}
