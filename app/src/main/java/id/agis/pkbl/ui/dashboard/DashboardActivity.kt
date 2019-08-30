package id.agis.pkbl.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.agis.pkbl.R
import id.agis.pkbl.ui.dashboard.kemitraan.DashboardKemitraanFragment
import id.agis.pkbl.util.TabAdapter
import kotlinx.android.synthetic.main.activity_dashboard.*
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener


class DashboardActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        view_pager.setOnTouchListener { v, _ ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            false
        }

        view_pager.addOnPageChangeListener(object : SimpleOnPageChangeListener() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                view_pager.parent.requestDisallowInterceptTouchEvent(true)
            }
        })

        val adapter = TabAdapter(supportFragmentManager)
        adapter.addFragment(DashboardKemitraanFragment(), "Bina Lingkungan")
        adapter.addFragment(DashboardKemitraanFragment(), "Kemitraan")
        tab_layout.setupWithViewPager(view_pager)
        view_pager.adapter = adapter


    }
}
