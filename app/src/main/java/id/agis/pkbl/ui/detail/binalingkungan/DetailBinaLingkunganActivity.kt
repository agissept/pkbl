package id.agis.pkbl.ui.detail.binalingkungan

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import id.agis.pkbl.R
import id.agis.pkbl.ui.map.MapActivity
import kotlinx.android.synthetic.main.activity_detail_bina_lingkungan.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import pub.devrel.easypermissions.EasyPermissions
import java.io.File


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
    private val listFile = mutableListOf<String?>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_bina_lingkungan)

//        val data = intent.getParcelableExtra<Pemohon>(EXTRA_DATA)

        setSupportActionBar(toolbar)
        collapsing_toolbar.setCollapsedTitleTextColor(
            ContextCompat.getColor(this, android.R.color.white)
        )
        collapsing_toolbar.setExpandedTitleColor(
            ContextCompat.getColor(this, android.R.color.black)
        )

        viewModel = ViewModelProviders.of(this).get(DetailBinaLingkunganViewModel::class.java)
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
        adapter = DetailBinaLingkunganAdapter(this, listFile)
        recycler_view.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapter
        }


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btn_set_coordinate.setOnClickListener {
            startActivityForResult<MapActivity>(OPEN_COORDINATE_REQUEST_CODE)
        }

        btn_set_dok_survey.setOnClickListener {
            if (EasyPermissions.hasPermissions(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                    type = "*/*"
                }
                startActivityForResult(intent, OPEN_DOCUMENT_REQUEST_CODE)
            } else {
                EasyPermissions.requestPermissions(
                    this,
                    "This application need your permission to access photo gallery.",
                    991,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                OPEN_COORDINATE_REQUEST_CODE -> {
                    tv_coordinate.text = data?.getStringExtra("result")
                }
                OPEN_DOCUMENT_REQUEST_CODE -> {
                    val listUri = mutableListOf<Uri>()
                    if (data != null) {
                        data.clipData?.let { clipData ->
                            for (i in 0 until clipData.itemCount) {
                                clipData.getItemAt(i)?.uri?.let { listUri.add(it) }
                            }
                        }
                    } else {
                        data?.data?.let { listUri.add(it) }
                    }


//                        val uri = data?.data
                    listUri.forEach {
                        println(it.toString() + "aaaaaaaaaaaaaaa")
                    }

//                    uri?.let { openFile(it) }
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

    fun uploadFile(pickedFile: String?) {
        val requestBody = RequestBody.create(MediaType.parse("multipart"), File(pickedFile))
        val file = MultipartBody.Part.createFormData(
            "imagename",
            File(pickedFile).name, requestBody
        )
        viewModel.uploadImage(file)

    }
}
