package id.agis.pkbl.ui.dashboard

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener
import id.agis.pkbl.R
import id.agis.pkbl.ui.dashboard.kemitraan.DashboardKemitraanFragment
import id.agis.pkbl.ui.login.LoginActivity
import id.agis.pkbl.util.TabAdapter
import kotlinx.android.synthetic.main.activity_dashboard.*
import org.jetbrains.anko.startActivity


class DashboardActivity : AppCompatActivity() {

    lateinit var viewModel: DashboardViewModel

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

        viewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_dashboard, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.logout ->{
                viewModel.deleteToken(this)
                startActivity<LoginActivity>()
                finish()
            }
        }

        return true
    }
}
