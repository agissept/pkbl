package id.agis.pkbl.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.agis.pkbl.data.ApiInterface
import id.agis.pkbl.data.RemoteRepository
import id.agis.pkbl.model.Pengajuan

class HomeViewModel : ViewModel() {
    private var _listPengajuan = MutableLiveData<List<Pengajuan>>()
    val listPengajuan get() = _listPengajuan

    fun getPenugasan(){
        _listPengajuan = RemoteRepository().getPenugasan("", "")
    }
}