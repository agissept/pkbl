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
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.vincent.filepicker.Constant.*
import com.vincent.filepicker.activity.ImagePickActivity
import com.vincent.filepicker.filter.entity.ImageFile
import id.agis.pkbl.R
import id.agis.pkbl.ui.binalingkungan.EditBinaLingkunganActivity
import id.agis.pkbl.ui.map.MapActivity
import kotlinx.android.synthetic.main.activity_detail_bina_lingkungan.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import pub.devrel.easypermissions.EasyPermissions
import java.io.File


class DetailBinaLingkunganActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_COORDINATE = 1
    }

    private lateinit var viewModel: DetailBinaLingkunganViewModel
    private lateinit var adapter: DetailBinaLingkunganAdapter
    private lateinit var backIcon: Drawable
    private lateinit var editIcon: Drawable
    lateinit var imageName: MultipartBody.Part
    val listImage = mutableListOf<String?>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_bina_lingkungan)

//        val data = intent.getParcelableExtra<BinaLingkugan>(EXTRA_DATA)

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
        adapter = DetailBinaLingkunganAdapter(this, listImage)
        recycler_view.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler_view.adapter = adapter

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btn_set_coordinate.setOnClickListener {
            startActivityForResult<MapActivity>(EXTRA_COORDINATE)
        }

        btn_set_dok_survey.setOnClickListener {
            if (EasyPermissions.hasPermissions(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                val i = Intent(this, ImagePickActivity::class.java)
                i.putExtra(MAX_NUMBER, 1)
                startActivityForResult(i, REQUEST_CODE_PICK_IMAGE)
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
        if (requestCode == EXTRA_COORDINATE) {
            if (resultCode == Activity.RESULT_OK) {
                tv_coordinate.text = data?.getStringExtra("result")
            }
        } else if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            val pickedImg =
                data?.getParcelableArrayListExtra<ImageFile>(RESULT_PICK_IMAGE)?.get(0)?.path
            listImage.add(pickedImg)
            adapter.notifyDataSetChanged()

            val requestBody = RequestBody.create(MediaType.parse("multipart"), File(pickedImg))
            imageName = MultipartBody.Part.createFormData(
                "imagename",
                File(pickedImg).name, requestBody
            )
//            viewModel.uploadImage(imageName)

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
