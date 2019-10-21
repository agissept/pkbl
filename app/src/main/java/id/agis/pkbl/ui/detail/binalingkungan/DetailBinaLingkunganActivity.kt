package id.agis.pkbl.ui.detail.binalingkungan

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import id.agis.pkbl.R
import id.agis.pkbl.constant.Constant
import id.agis.pkbl.constant.Constant.Companion.USER_ID
import id.agis.pkbl.constant.Constant.Companion.USER_NAME
import id.agis.pkbl.constant.Constant.Companion.USER_ROLE
import id.agis.pkbl.constant.Constant.Companion.USER_TOKEN
import id.agis.pkbl.data.local.entities.FileEntity
import id.agis.pkbl.data.local.entities.PemohonEntity
import id.agis.pkbl.model.Access
import id.agis.pkbl.model.User
import id.agis.pkbl.ui.map.MapActivity
import id.agis.pkbl.util.FileUtil.getMimeType
import id.agis.pkbl.util.FileUtil.getPath
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
    private lateinit var user: User
    private lateinit var pemohonEntity: PemohonEntity
    private val listFile = mutableListOf<FileEntity>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_bina_lingkungan)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        pemohonEntity = intent.getParcelableExtra(EXTRA_DATA)

        getSharedPreferences(Constant.LOGIN_STATUS, Context.MODE_PRIVATE).apply {
            val username = getString(USER_NAME, "default")!!
            val role = getInt(USER_ROLE, 0)
            val id = getInt(USER_ID, 0)
            val token = getString(USER_TOKEN, "default")!!
            val access = Access(role, "")
            user = User(id, username, access, token)
        }

        viewModel =
            ViewModelFactory.getInstance(this).create(DetailBinaLingkunganViewModel::class.java)


        val activity: Activity = this
        adapter = DetailBinaLingkunganAdapter(this, listFile).apply {
            onItemClickFile = {
                openFile(it.uri?.toUri()!!)
                toast(it.uri!!)
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
            //            viewModel.uploadFile(listFile, username!!, pemohon.idPemohon)
        }

        viewModel.idPemohon = 0
//        viewModel.listFile.observe(this, Observer {
//            listFile.clear()
//            listFile.addAll(it)
//            adapter.notifyDataSetChanged()
//        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                OPEN_COORDINATE_REQUEST_CODE -> {
                    tv_coordinate.text = data?.getStringExtra("result")
                }
                OPEN_DOCUMENT_REQUEST_CODE -> {
                    val listUri = viewModel.getUri(data)
                    listUri.forEach {

                        val target = viewModel.copyFile(it.getPath(this), user.username)
                        val expectation = it
                        val reality = target.path
                        val extra = Uri.parse("file://" + reality)
                        Log.i("aaaaaa","e: $expectation" +
                                "\n r: $reality" +
                                "\n $extra" +
                                "\n aaaaaaaaaaaaaaaaaaaaaaa")
                        val fileEntity = FileEntity(
                            target.name,
                            it.getMimeType(this)!!,
                            pemohonEntity.idPemohon,
                            user.userId,
                            null,
                            Uri.parse(target.toString()).toString(),
                            target.path,
                            uploaded = false,
                            downloaded = true
                        )
                        listFile.add(fileEntity)
//                        viewModel.insertFile(fileEntity)
                    }
                    adapter.notifyDataSetChanged()


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
