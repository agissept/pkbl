package id.agis.pkbl.ui.detail.binalingkungan

import android.app.Activity
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import id.agis.pkbl.R
import id.agis.pkbl.model.BinaLingkugan
import id.agis.pkbl.ui.binalingkungan.EditBinaLingkunganActivity
import id.agis.pkbl.ui.map.MapActivity
import kotlinx.android.synthetic.main.activity_detail_bina_lingkungan.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult


class DetailBinaLingkunganActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_COORDINATE = 1
    }

    private lateinit var backIcon: Drawable
    private lateinit var editIcon: Drawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_bina_lingkungan)

        val data = intent.getParcelableExtra<BinaLingkugan>(EXTRA_DATA)

        setSupportActionBar(toolbar)
        collapsing_toolbar.setCollapsedTitleTextColor(
            ContextCompat.getColor(this, android.R.color.white)
        )
        collapsing_toolbar.setExpandedTitleColor(
            ContextCompat.getColor(this, android.R.color.black)
        )


        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            var scrollRange = -1

            if (scrollRange == -1) {
                scrollRange = appbar.totalScrollRange
            }
            if (scrollRange + verticalOffset == 0) {
                backIcon.setColorFilter(
                    ContextCompat.getColor(this, android.R.color.white),
                    PorterDuff.Mode.SRC_ATOP
                )
                editIcon.setColorFilter(
                    ContextCompat.getColor(this, android.R.color.white), PorterDuff.Mode.SRC_ATOP
                )
            } else if (::editIcon.isInitialized) {
                backIcon.setColorFilter(
                    ContextCompat.getColor(this, android.R.color.black), PorterDuff.Mode.SRC_ATOP
                )
                editIcon.setColorFilter(
                    ContextCompat.getColor(this, android.R.color.black), PorterDuff.Mode.SRC_ATOP
                )
            }
        })

//        tv_nama.text = data.title
//        tv_alamat.text = data.body
        recycler_view.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler_view.adapter = DetailBinaLingkunganAdapter(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btn_set_coordinate.setOnClickListener {
            startActivityForResult<MapActivity>(EXTRA_COORDINATE)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EXTRA_COORDINATE) {
            if (resultCode == Activity.RESULT_OK) {
                tv_coordinate.text = data?.getStringExtra("result")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        if (menu != null) {
            backIcon = toolbar.navigationIcon!!
            editIcon = menu.findItem(R.id.edit).icon
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
            R.id.edit -> {
                startActivity<EditBinaLingkunganActivity>()
            }
        }

        return true
    }
}
