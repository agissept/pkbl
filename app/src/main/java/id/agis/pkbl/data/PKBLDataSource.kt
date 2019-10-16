package id.agis.pkbl.data

import androidx.lifecycle.LiveData
import id.agis.pkbl.data.local.entities.PemohonEntity
import id.agis.pkbl.model.UploadFileResponse
import id.agis.pkbl.model.User
import id.agis.pkbl.vo.Resource
import io.realm.RealmResults
import okhttp3.MultipartBody


interface PKBLDataSource {
    fun loginRequest(email: String, pass: String): LiveData<User>

    fun getAllKemitraan(): LiveData<Resource<RealmResults<PemohonEntity>>>

    fun getAllBinaLingkungan(): LiveData<Resource<RealmResults<PemohonEntity>>>

    fun uploadFile(file: MultipartBody.Part, idPemohon: Int): LiveData<UploadFileResponse>
}