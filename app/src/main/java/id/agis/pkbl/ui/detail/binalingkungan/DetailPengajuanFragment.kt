package id.agis.pkbl.ui.detail.binalingkungan

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import id.agis.pkbl.R
import id.agis.pkbl.constant.Constant
import id.agis.pkbl.data.local.entities.FileEntity
import id.agis.pkbl.ui.map.MapActivity
import id.agis.pkbl.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_detail_pengajuan.*
import org.jetbrains.anko.support.v4.startActivityForResult
import org.jetbrains.anko.support.v4.toast
import pub.devrel.easypermissions.EasyPermissions


class DetailPengajuanFragment : Fragment() {

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val OPEN_COORDINATE_REQUEST_CODE = 110
        const val OPEN_DOCUMENT_REQUEST_CODE = 111
    }

    private lateinit var viewModel: DetailPengajuanViewModel
    private lateinit var adapter: DetailBinaLingkunganAdapter
    private val listFile = mutableListOf<FileEntity>()
    private var koordinat = ""
    private var source = ""
    var user = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_pengajuan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).appbar?.elevation = 0f

        val argument = arguments?.let { DetailPengajuanFragmentArgs.fromBundle(it) }

        source = argument!!.source

        val pengajuan = argument.pengajuan

        tv_nama.setText(pengajuan.nama)
        tv_nik.setText(pengajuan.nik)
        tv_tujuan.setText(pengajuan.tujuan)
        tv_dana.setText(pengajuan.dana)
        tv_sektor.setText(pengajuan.sektor)
        tv_provinsi.setText(pengajuan.provinsi)
        tv_kota.setText(pengajuan.kota)
        tv_kecamatan.setText(pengajuan.kecamatan)
        tv_kelurahan.setText(pengajuan.kelurahan)
        tv_jalan.setText(pengajuan.jalan)
        tv_alasan.setText(pengajuan.alasan)
        tv_instansi.setText(pengajuan.instansi)
        tv_penilaian.setText(pengajuan.penilaian)

        val sharedPreferences = context!!.getSharedPreferences(Constant.USER, Context.MODE_PRIVATE)
        user = sharedPreferences.getString(Constant.USER_NAME, "")!!
        val role = sharedPreferences.getString(Constant.USER_ROLE, "")

        println("$role  $source  aaaaaaaaaaaaaaaaaaaaaaaaaaa")
        if (role == "surveyor" && source == "penugasan") {
            btn_set_coordinate.visibility = View.VISIBLE
            btn_upload_file.visibility = View.VISIBLE
            btn_simpan.visibility = View.VISIBLE
        } else if (role == "penilai" && source == "penilaian") {
            btn_penilaian.visibility = View.VISIBLE
            tv_penilaian.isFocusable = true
            tv_penilaian.isEnabled = true
            tv_penilaian.isFocusable = true
        } else if (role == "penyetuju" && source == "persetujuan") {
            btn_persetujuan.visibility = View.VISIBLE
            tv_alasan.isFocusable = true
            tv_alasan.isEnabled = true
            tin_alasan.isFocusable = true
        } else if (role == "bendahara" && source == "pencairan") {
            btn_pencairan.visibility = View.VISIBLE
        }

//
//        getSharedPreferences(Constant.LOGIN_STATUS, Context.MODE_PRIVATE).apply {
//            val username = getString(USER_NAME, "default")!!
//            val role = getInt(USER_ROLE, 0)
//            val id = getInt(USER_ID, 0)
//            val token = getString(USER_TOKEN, "default")!!
//            val access = Access(role, "")
//            user = User(id, username, access, token)
//        }

        viewModel =
            ViewModelFactory.getInstance(context!!).create(DetailPengajuanViewModel::class.java)

        adapter = DetailBinaLingkunganAdapter(context!!, listFile).apply {
            onItemClickFile = {
                openFile(it.uri?.toUri()!!)
                toast(it.uri!!)
            }
            onItemClick = {
                if (checkPermission()) {
                    openFileManager()
                } else {
                    requestPermissions()
                }
            }
        }

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = adapter

        btn_set_coordinate.setOnClickListener {
            startActivityForResult<MapActivity>(OPEN_COORDINATE_REQUEST_CODE)
        }

        btn_upload_file.setOnClickListener {
            //            viewModel.uploadFile(listFile, username!!, pemohon.idPemohon)
        }

        btn_simpan.setOnClickListener {
            if (koordinat != "") {
                viewModel.postPenugasan(pengajuan.id, koordinat)
            } else {
                toast("Koordinat tidak boleh kosong")
            }
        }

        btn_penilaian.setOnClickListener {
            if (tv_penilaian.text.toString() != "") {
                viewModel.postPenilaian(pengajuan.id, user, tv_penilaian.text.toString())
            } else {
                toast("Penilaian tidak boleh kosong")
            }
        }

        btn_setuju.setOnClickListener {
            if (tv_alasan.text.toString() != "") {
                viewModel.postPersetujuan(pengajuan.id, user, true, tv_alasan.text.toString())
            } else {
                toast("Alasan tidak boleh kosong")
            }
        }
        btn_tolak.setOnClickListener {
            if (tv_alasan.text.toString() != "") {
                viewModel.postPersetujuan(pengajuan.id, user, false, tv_alasan.text.toString())
            } else {
                toast("Alasan tidak boleh kosong")
            }
        }

        btn_pencairan.setOnClickListener {
            viewModel.postPencairan(pengajuan.id, user)
        }

        viewModel.status.observe(this, Observer {
            toast(it.status.toString())
        })

//        viewModel.idPemohon = 0
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
                    setKoordinat(data!!)
                }
//                OPEN_DOCUMENT_REQUEST_CODE -> {
////                    val listUri = viewModel.getUri(data)
//                    listUri.forEach {
//
//                        val target = viewModel.copyFile(it.getPath(context!!), user.username)
//                        val expectation = it
//                        val reality = target.path
//                        val extra = Uri.parse("file://" + reality)
//                        Log.i("aaaaaa","e: $expectation" +
//                                "\n r: $reality" +
//                                "\n $extra" +
//                                "\n aaaaaaaaaaaaaaaaaaaaaaa")
//                        val fileEntity = FileEntity(
//                            target.name,
//                            it.getMimeType(context!!)!!,
//                            pemohonEntity.idPemohon,
//                            user.userId,
//                            null,
//                            Uri.parse(target.toString()).toString(),
//                            target.path,
//                            uploaded = false,
//                            downloaded = true
//                        )
//                        listFile.add(fileEntity)
////                        viewModel.insertFile(fileEntity)
//                    }
//                    adapter.notifyDataSetChanged()
//
//
//                }
            }
        }
    }

    private fun setKoordinat(data: Intent) {
        koordinat = data.getStringExtra("result")
        tv_latitude.setText(
            koordinat.substring(
                koordinat.indexOf('(') + 1,
                koordinat.indexOf(',')
            )
        )
        tv_longitude.setText(
            koordinat.substring(
                koordinat.indexOf(',') + 1,
                koordinat.indexOf(')')
            )
        )
    }

    private fun openFileManager() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            type = "*/*"
        }
        startActivityForResult(intent, OPEN_DOCUMENT_REQUEST_CODE)
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

    private fun checkPermission(): Boolean {
        return EasyPermissions.hasPermissions(context!!, Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun requestPermissions() {
        val rationale = "This application need your permission to access directory."
        EasyPermissions.requestPermissions(
            activity as Activity,
            rationale,
            991,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }


//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_detail, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        when (item?.itemId) {
//            android.R.id.home -> {
//                onBackPressed()
//            }
//            R.id.edit -> {
////                startActivity<EditBinaLingkunganActivity>()
//            }
//        }
//
//        return true
//    }
//
//    override fun onContextItemSelected(item: MenuItem?): Boolean {
//        if (item?.itemId == 123) {
//            viewModel.deleteFile(item.groupId)
//        }
//        return super.onContextItemSelected(item)
//    }

}
