package id.agis.pkbl.ui.home

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import id.agis.pkbl.R
import id.agis.pkbl.constant.Constant
import id.agis.pkbl.ui.home.binalingkungan.BinaLingkunganFragment
import id.agis.pkbl.ui.home.kemitraan.KemitraanFragment
import id.agis.pkbl.ui.login.LoginActivity
import id.agis.pkbl.util.TabAdapter
import kotlinx.android.synthetic.main.activity_unused.*
import org.jetbrains.anko.startActivity

class UnusedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unused)

        val adapter = TabAdapter(supportFragmentManager)
        adapter.addFragment(BinaLingkunganFragment(), "Bina Lingkungan")
        adapter.addFragment(KemitraanFragment(), "Pemohon")
        tab_layout.setupWithViewPager(view_pager)
        view_pager.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_dashboard, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.logout ->{
                deleteToken(this)
                startActivity<LoginActivity>()
                finish()
            }
        }

        return true
    }

    private fun deleteToken(context: Context){
        val sharedPreferences = context.getSharedPreferences(Constant.LOGIN_STATUS, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(Constant.USER_ID, null)
        editor.putString(Constant.USER_ROLE, null)
        editor.apply()
    }
}