package id.agis.pkbl.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.agis.pkbl.data.RemoteRepository
import id.agis.pkbl.model.FileModel
import id.agis.pkbl.model.Status
import id.agis.pkbl.model.UploadFileResponse
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.MediaType.Companion.toMediaType
import android.util.Log
import androidx.lifecycle.LiveData
import java.io.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import id.agis.pkbl.util.FileUtil.writeResponseBodyToDisk


class DetailPengajuanViewModel : ViewModel() {
    var _status = MutableLiveData<Status>()
    val status get() = _status
    var _response = MutableLiveData<UploadFileResponse>()
    val response get() =  _response
    var _listFileFromServer = MutableLiveData<List<UploadFileResponse>>()
    val listFileFromServer get() = _listFileFromServer
    var _downloadStatus = MutableLiveData<String>()
    val downloadStatus get() =  _downloadStatus

    fun postPenugasan(id: String, koordinat: String) {
        _status = RemoteRepository().postPenugasan(id, koordinat)
    }

    fun postPenilaian(id: String, user: String, penilaian: String) {
        _status = RemoteRepository()
            .postPenilaian(id, user, penilaian)
    }

    fun postPersetujuan(
        id: String,
        user: String,
        statusPersetujuan: Boolean,
        alasan: String
    ) {
        _status = RemoteRepository().postPersetujuan(id, user, statusPersetujuan, alasan)
    }

    fun postPencairan(id: String, user: String) {
        _status = RemoteRepository().postPencairan(id, user)
    }

    fun getListFileFromServer(idPengajuan: String = ""){
        _listFileFromServer = RemoteRepository().getFiles(idPengajuan)
    }

    fun uploadFile(listFile: List<FileModel>, idPemohon: String) {
        listFile.forEach {
            val file = File(it.path)

            val requestBody = file.asRequestBody("multipart/file".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData("file", file.name, requestBody)

            _response  = RemoteRepository().uploadFile(multipartBody, idPemohon)
        }
    }

    fun downloadFile(id: String, name: String): LiveData<ResponseBody>{
        return RemoteRepository().downloadFile(id, name)
    }

    fun writeToDisk(responseBody: ResponseBody, name: String, idPengajuan: String, loadingInterface: LoadingInterface){
        doAsync {
            val writtenToDisk = writeResponseBodyToDisk(responseBody, name, idPengajuan, loadingInterface)
            uiThread {
                Log.d("AAA", "file download was a success? $writtenToDisk")
                downloadStatus.value = name
            }
        }
    }



}