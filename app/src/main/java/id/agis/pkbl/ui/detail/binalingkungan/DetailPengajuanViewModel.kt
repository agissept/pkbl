package id.agis.pkbl.ui.detail.binalingkungan

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.agis.pkbl.data.PKBLRepository
import id.agis.pkbl.data.local.entities.FileEntity
import id.agis.pkbl.data.remote.RemoteRepository
import id.agis.pkbl.data.remote.retrofit.ApiClient
import id.agis.pkbl.data.remote.retrofit.ApiInterface
import id.agis.pkbl.model.Status
import id.agis.pkbl.util.FileUtil.copyFile
import id.agis.pkbl.util.FileUtil.createFolder
import io.realm.RealmResults
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class DetailPengajuanViewModel(private val pkblRepository: PKBLRepository) : ViewModel() {
    private val apiInterface: ApiInterface = ApiClient.retrofit().create(ApiInterface::class.java)

    var idPemohon = 0
    var _status = MutableLiveData<Status>()
    val status get() = _status

    val listFile: LiveData<RealmResults<FileEntity>> get() = pkblRepository.getAllFile(idPemohon)

    fun postPenugasan(id: Int, koordinat: String) {
        _status = RemoteRepository(apiInterface).postPenugasan(id, koordinat)
    }

    fun postPenilaian(id: Int, user: String, penilaian: String) {
        _status = RemoteRepository(apiInterface).postPenilaian(id, user, penilaian)
    }

    fun postPersetujuan(
        id: Int,
        user: String,
        statusPersetujuan: Boolean,
        alasan: String
    ) {
        _status = RemoteRepository(apiInterface).postPersetujuan(id, user, statusPersetujuan, alasan)
    }

    fun postPencairan(id: Int, user: String) {
        _status = RemoteRepository(apiInterface).postPencairan(id, user)
    }

    fun uploadFile(listFile: List<FileEntity>, idPemohon: Int) {
        listFile.forEach {
            val file = File(it.path)
            val requestBody = RequestBody.create(MediaType.parse("multipart"), file)
            val multipartBody = MultipartBody.Part.createFormData("files", file.name, requestBody)

            pkblRepository.uploadFile(multipartBody, idPemohon)
        }
    }

    fun getUri(data: Intent?): List<Uri> {
        val listUri = mutableListOf<Uri>()
        if (data?.clipData != null) {
            data.clipData?.let { clipData ->
                for (i in 0 until clipData.itemCount) {
                    clipData.getItemAt(i)?.uri?.let {
                        listUri.add(it)
                    }
                }
            }
        } else {
            data?.data?.let {
                listUri.add(it)
//                listFile.add(
//                    UserFile(
//                        (it),
//                        getPath(context, it),
//                        getFileName(it, context),
//                        getMimeType(it, context)
//                    )
//                )
            }
        }

        return listUri
    }

    fun deleteFile(position: Int) {
//        listFile.removeAt(position - 1)
//        listFileLiveData.postValue(listFile)
    }

    fun copyFile(path: String, username: String): File {
        val file = File(path)
        val target = createFolder(
            username,
            "Bina Lingkungan",
            "Upload",
            System.currentTimeMillis().toString() + file.name
        )

        copyFile(file, target)

        return file
    }

    fun insertFile(fileEntity: FileEntity) {
        pkblRepository.insertFile(fileEntity)
    }
}