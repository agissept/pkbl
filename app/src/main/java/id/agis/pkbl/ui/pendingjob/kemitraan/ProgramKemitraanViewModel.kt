package id.agis.pkbl.ui.pendingjob.kemitraan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.agis.pkbl.data.RemoteRepository
import id.agis.pkbl.data.ApiClient
import id.agis.pkbl.data.ApiInterface
import id.agis.pkbl.model.Pengajuan

class ProgramKemitraanViewModel: ViewModel(){
    private val apiInterface: ApiInterface = ApiClient.retrofit().create(
        ApiInterface::class.java)
    private var _listPengajuan = MutableLiveData<List<Pengajuan>>()
    val listPengajuan get() = _listPengajuan

    fun getPenugasan(){
        _listPengajuan = RemoteRepository(apiInterface)
            .getPenugasan("kemitraan", "")
    }

}