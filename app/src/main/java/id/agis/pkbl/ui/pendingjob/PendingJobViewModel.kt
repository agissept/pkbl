package id.agis.pkbl.ui.pendingjob

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.agis.pkbl.data.RemoteRepository
import id.agis.pkbl.data.ApiClient
import id.agis.pkbl.data.ApiInterface
import id.agis.pkbl.model.Pengajuan

class PendingJobViewModel: ViewModel(){
    private var _listPengajuan = MutableLiveData<List<Pengajuan>>()
    val listPengajuan get() = _listPengajuan

    fun getPenugasan(type: String){
        _listPengajuan = RemoteRepository().getPenugasan(type, "")
    }

}