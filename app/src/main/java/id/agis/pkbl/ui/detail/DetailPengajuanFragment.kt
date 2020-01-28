package id.agis.pkbl.ui.detail

import android.Manifest
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.DisplayMetrics
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import id.agis.pkbl.R
import id.agis.pkbl.constant.Constant
import id.agis.pkbl.model.FileModel
import id.agis.pkbl.model.Pengajuan
import id.agis.pkbl.ui.map.MapActivity
import id.agis.pkbl.util.FileUtil.copyFile
import id.agis.pkbl.util.FileUtil.createNewFolder
import id.agis.pkbl.util.getFileModelsFromFiles
import id.agis.pkbl.util.getFilesFromPath
import kotlinx.android.synthetic.main.fragment_detail_pengajuan.*
import org.jetbrains.anko.support.v4.startActivityForResult
import org.jetbrains.anko.support.v4.toast
import pub.devrel.easypermissions.EasyPermissions
import id.agis.pkbl.util.FileUtil.getUri
import id.agis.pkbl.util.FileUtil.getPath
import id.agis.pkbl.util.launchFileIntent
import id.agis.pkbl.util.FileUtil.deleteFile as FileUtilsDeleteFile
import id.agis.pkbl.util.FileType
import java.io.File


class DetailPengajuanFragment : Fragment(R.layout.fragment_detail_pengajuan), LoadingInterface {

    companion object {
        const val OPEN_COORDINATE_REQUEST_CODE = 110
        const val OPEN_DOCUMENT_REQUEST_CODE = 111

        private const val OPTIONS_DIALOG_TAG: String = "id.agis.pkbl.main.options_dialog"
    }

    private lateinit var menu: Menu
    private lateinit var viewModel: DetailPengajuanViewModel
    private lateinit var fileListAdapter: FileListAdapter
    private lateinit var statusAdapter: StatusPengajuanAdapter
    private lateinit var argument: DetailPengajuanFragmentArgs
    private lateinit var pengajuan: Pengajuan
    private val listFile = mutableListOf<FileModel>()
    private var backdropShown = false
    private var koordinat = ""
    private var isAddFileActive = false
    var user = ""
    lateinit var recyclerViewState: Parcelable
    lateinit var progressDialog: ProgressDialog

    private fun writeToDiskObserver() {
        viewModel.downloadStatus.observe(this, Observer { statusDownload ->
            if (statusDownload != "") {
                replaceFile("storage/emulated/0/pkbl/Download/${pengajuan.id}/$statusDownload")

                toast("Download Completed $statusDownload")
            }
        })
    }

    private fun downloadFileObserver(name: String) {
        progressDialog = ProgressDialog(context)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Downloading...")
        progressDialog.show()
        viewModel.downloadFile(pengajuan.id, name).observe(this, Observer {
            viewModel.writeToDisk(it, name, pengajuan.id, this)
            writeToDiskObserver()
            progressDialog.hide()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(DetailPengajuanViewModel::class.java)
        argument = arguments.let {
            DetailPengajuanFragmentArgs.fromBundle(it!!)
        }
        pengajuan = argument.pengajuan

        val sharedPreferences = context!!.getSharedPreferences(Constant.USER, Context.MODE_PRIVATE)
        user = sharedPreferences.getString(Constant.USER_USERNAME, "")!!
        val role = sharedPreferences.getString(Constant.USER_ROLE, "")!!

        viewModel.status.observe(this, Observer {
            toast(it.status.toString())
        })

        initTextView()
        initRole(role)
        initButton()
        initRecyclerStatus()
        initRecyclerFile()

        if (isAddFileActive) {
            createNewFolder(pengajuan.id, "Upload")
        } else {
            createNewFolder(pengajuan.id, "Download")
        }

        initListFile()
    }

    private fun initRecyclerStatus() {
        statusAdapter = StatusPengajuanAdapter(pengajuan)
        rv_status.adapter = statusAdapter
        rv_status.layoutManager = LinearLayoutManager(context!!)
    }

    private fun initRecyclerFile() {
        fileListAdapter = FileListAdapter(context!!, listFile, isAddFileActive).apply {
            onItemAddClickListener = {
                if (checkPermission()) {
                    openFileManager()
                } else {
                    requestPermissions()
                }
            }

            onItemClickListener = {
                if(it.path == ""){
                    downloadFileObserver(it.name)
                }else {
                    context!!.launchFileIntent(it)
                }
            }

            onItemLongClickListener = {
                val optionsDialog = FileOptionsDialog.build {}
                optionsDialog.onDeleteClickListener = {
                    FileUtilsDeleteFile(it.path)
                    initListFile()
                    toast("'${it.name}' deleted successfully.")
                }

                optionsDialog.show(childFragmentManager, OPTIONS_DIALOG_TAG)
            }
        }

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        rv_file.layoutManager?.onRestoreInstanceState(recyclerViewState)
        rv_file.layoutManager = layoutManager
        rv_file.adapter = fileListAdapter
    }

    private fun initTextView() {
        tv_id_pengajuan.setText(pengajuan.id)
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
        setKoordinat(pengajuan.koordinat)
    }

    private fun initRole(role: String) {
        val source = argument.source

        if (role == "Surveyor" && (source == "penugasan" || source == "home")) {
            btn_set_coordinate.visibility = View.VISIBLE
            btn_simpan.visibility = View.VISIBLE
            isAddFileActive = true
        } else if (role == "Penilai" && (source == "penilaian" || source == "home")) {
            btn_penilaian.visibility = View.VISIBLE
            tv_penilaian.isFocusable = true
            tv_penilaian.isEnabled = true
            tv_penilaian.isFocusable = true
        } else if (role == "Penyetuju" && (source == "persetujuan" || source == "home")) {
            btn_persetujuan.visibility = View.VISIBLE
            tv_alasan.isFocusable = true
            tv_alasan.isEnabled = true
            tin_alasan.isFocusable = true
        } else if (role == "Bendahara" && (source == "pencairan" || source == "home")) {
            btn_pencairan.visibility = View.VISIBLE
        }
    }

    private fun initButton() {
        btn_set_coordinate.setOnClickListener {
            startActivityForResult<MapActivity>(OPEN_COORDINATE_REQUEST_CODE)
        }

        btn_simpan.setOnClickListener {
            if (koordinat != "") {
                viewModel.uploadFile(listFile, pengajuan.id)
                progressDialog = ProgressDialog(context)
                progressDialog.setCancelable(false)
                progressDialog.setMessage("Uploading...")
                progressDialog.show()
                viewModel.postPenugasan(pengajuan.id, koordinat)
                if(listFile.size < 1){
                    viewModel.status.observe(this, Observer{
                        toast("Data berhasil diunggah")
                        progressDialog.hide()
                        btn_set_coordinate.isEnabled = false
                        btn_simpan.isEnabled = false
                    })
                }else {
                    viewModel.response.observe(this, Observer {
                        toast("Data berhasil diunggah")
                        progressDialog.hide()
                        btn_set_coordinate.isEnabled = false
                        btn_simpan.isEnabled = false
                    })
                }
            } else {
                toast("Koordinat dan berkas tidak boleh kosong")
            }
        }

        btn_penilaian.setOnClickListener {
            if (tv_penilaian.text.toString() != "") {
                viewModel.postPenilaian(pengajuan.id, user, tv_penilaian.text.toString())
                viewModel.status.observe(this, Observer {
                    toast("Data berhasil diunggah")
                })
            } else {
                toast("Penilaian tidak boleh kosong")
            }
        }

        btn_setuju.setOnClickListener {
            if (tv_alasan.text.toString() != "") {
                viewModel.postPersetujuan(pengajuan.id, user, true, tv_alasan.text.toString())
                viewModel.status.observe(this, Observer {
                    toast("Data berhasil diunggah")
                })
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
            viewModel.status.observe(this, Observer {
                toast("Pecairan berhasil dilakukan")
            })
        }
    }

    private fun initListFile() {
        listFile.clear()
        if (isAddFileActive) {
            listFile.addAll(getFileModelsFromFiles(getFilesFromPath("storage/emulated/0/pkbl/Upload/${pengajuan.id}")))
        } else {
            listFile.addAll(getFileModelsFromFiles(getFilesFromPath("storage/emulated/0/pkbl/Download/${pengajuan.id}")))
            viewModel.getListFileFromServer(pengajuan.id)
            viewModel.listFileFromServer.observe(this, Observer {
                it.forEachIndexed { index, fileModel ->
                    val listName = listFile.map {
                        it.name
                    }
                    if (!listName.contains(fileModel.nama)) {
                        val fileModel =
                            FileModel("", FileType.FILE, fileModel.nama, 0.toDouble(), "", 0)
                        listFile.add(fileModel)
                    }
                }.let {
                    fileListAdapter.notifyDataSetChanged()
                }
            })
        }
        recyclerViewState = rv_file.layoutManager?.onSaveInstanceState()!!
        fileListAdapter.notifyDataSetChanged()
        rv_file.layoutManager?.onRestoreInstanceState(recyclerViewState)
    }

    private fun replaceFile(path: String){
        val file = File(path)
        val temp = listOf(file)
        val fileModel = getFileModelsFromFiles(temp)[0]
        val index = listFile.map{ it.name}.indexOf(fileModel.name)
        listFile[index] = fileModel
        recyclerViewState = rv_file.layoutManager?.onSaveInstanceState()!!
        fileListAdapter.notifyDataSetChanged()
        rv_file.layoutManager?.onRestoreInstanceState(recyclerViewState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                OPEN_COORDINATE_REQUEST_CODE -> {
                    val koordinat = data!!.getStringExtra("result")
                    this.koordinat = koordinat
                    setKoordinat(koordinat)
                }
                OPEN_DOCUMENT_REQUEST_CODE -> {
                    setFile(data!!)
                }
            }
        }
    }

    private fun setFile(data: Intent) {
        val listUri = data.getUri()
        listUri.forEach {
            val path = it.getPath(context!!)
            copyFile(path, pengajuan.id)
        }
        initListFile()
    }

    private fun setKoordinat(koordinat: String) {
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_dashboard, menu)
        this.menu = menu
        val item = menu.findItem(R.id.action_settings)
        item.isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filter -> {
                showBackdrop()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showBackdrop() {
        val animatorSet = AnimatorSet()

        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)

        backdropShown = !backdropShown

        // Cancel the existing animations
        animatorSet.removeAllListeners()
        animatorSet.end()
        animatorSet.cancel()

        val translateY = backdrop.height - 6

        val animator = ObjectAnimator.ofFloat(
            product_grid,
            "translationY",
            (if (backdropShown) translateY else 0).toFloat()
        )
        animator.duration = 500

        animatorSet.play(animator)
        animator.start()

        updateIcon()
    }

    private fun updateIcon() {
        if (backdropShown) {
            menu.findItem(R.id.filter).icon = context?.getDrawable(R.drawable.ic_close_black_24dp)
        } else {
            menu.findItem(R.id.filter).icon = context?.getDrawable(R.drawable.ic_filter)
        }
    }


    override fun dowloandProgress(progress: Int) {
        println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa $progress")
    }
}
