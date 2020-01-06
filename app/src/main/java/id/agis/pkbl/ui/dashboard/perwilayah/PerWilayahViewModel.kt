package id.agis.pkbl.ui.dashboard.perwilayah

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.agis.pkbl.data.remote.RemoteRepository
import id.agis.pkbl.data.remote.retrofit.ApiClient
import id.agis.pkbl.data.remote.retrofit.ApiInterface
import id.agis.pkbl.model.Pengajuan

class PerPropinsiViewModel : ViewModel() {
    private val apiInterface: ApiInterface = ApiClient.retrofit().create(ApiInterface::class.java)
    private var _dataPengajuan = MutableLiveData<List<Pengajuan>>()
    val dataPengajuan get() = _dataPengajuan

    fun getPengajuan(groupBy: String, bulan: String, tahun: String, type: String){
        _dataPengajuan = RemoteRepository(apiInterface).getPengajuan(groupBy, bulan, tahun, type)
    }

}