package id.agis.pkbl.ui.detail.binalingkungan

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import id.agis.pkbl.R
import id.agis.pkbl.constant.Constant
import id.agis.pkbl.constant.Constant.Companion.USER_NAME
import id.agis.pkbl.data.local.entities.PemohonEntity
import id.agis.pkbl.model.UserFile
import id.agis.pkbl.ui.map.MapActivity
import id.agis.pkbl.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_detail_bina_lingkungan.*
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import pub.devrel.easypermissions.EasyPermissions


class DetailBinaLingkunganActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val OPEN_COORDINATE_REQUEST_CODE = 110
        const val OPEN_DOCUMENT_REQUEST_CODE = 111
    }

    private lateinit var viewModel: DetailBinaLingkunganViewModel
    private lateinit var adapter: DetailBinaLingkunganAdapter
    private lateinit var backIcon: Drawable
    private lateinit var editIcon: Drawable
    private val listFile = mutableListOf<UserFile>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_bina_lingkungan)

        val pemohon = intent.getParcelableExtra<PemohonEntity>(EXTRA_DATA)
        val username = getSharedPreferences(Constant.LOGIN_STATUS, Context.MODE_PRIVATE).getString(
            USER_NAME,
            "default"
        )

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        collapsing_toolbar.setCollapsedTitleTextColor(
            ContextCompat.getColor(this, android.R.color.white)
        )
        collapsing_toolbar.setExpandedTitleColor(
            ContextCompat.getColor(this, android.R.color.black)
        )

        viewModel =
            ViewModelFactory.getInstance(this).create(DetailBinaLingkunganViewModel::class.java)

        val activity: Activity = this
        adapter = DetailBinaLingkunganAdapter(this, listFile).apply {
            onItemClickFile = {
                openFile(it.uri)
            }
            onItemClick = {
                if (EasyPermissions.hasPermissions(
                        activity,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                ) {
                    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                        type = "*/*"
                    }
                    startActivityForResult(intent, OPEN_DOCUMENT_REQUEST_CODE)
                } else {
                    val rationale = "This application need your permission to access photo gallery."
                    EasyPermissions.requestPermissions(
                        activity, rationale, 991,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                }
            }
        }

        recycler_view.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler_view.adapter = adapter


        btn_set_coordinate.setOnClickListener {
            startActivityForResult<MapActivity>(OPEN_COORDINATE_REQUEST_CODE)
        }

        btn_upload_file.setOnClickListener {
            viewModel.uploadFile(listFile, username!!, pemohon.idPemohon)
        }

        viewModel.listFileLiveData.observe(this, Observer {
            listFile.clear()
            listFile.addAll(it)
            adapter.notifyDataSetChanged()
        })

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
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                OPEN_COORDINATE_REQUEST_CODE -> {
                    tv_coordinate.text = data?.getStringExtra("result")
                }
                OPEN_DOCUMENT_REQUEST_CODE -> {
                    viewModel.insertUri(data, this)
                }
            }
        }
    }

    private fun openFile(uri: Uri) {
        try {
            val openIntent = Intent(Intent.ACTION_VIEW).apply {
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                data = uri
            }
            startActivity(openIntent)
        } catch (ex: ActivityNotFoundException) {
            toast("No Activity")
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
//                startActivity<EditBinaLingkunganActivity>()
            }
        }

        return true
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == 123) {
            viewModel.deleteFile(item.groupId)
        }
        return super.onContextItemSelected(item)
    }

}
