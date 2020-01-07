package id.agis.pkbl.ui.dashboard.persektor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.agis.pkbl.data.RemoteRepository
import id.agis.pkbl.data.ApiClient
import id.agis.pkbl.data.ApiInterface

import id.agis.pkbl.model.Pengajuan

class PerSektorViewModel : ViewModel() {
    private val apiInterface: ApiInterface = ApiClient.retrofit().create(
        ApiInterface::class.java)
    private var _dataPengajuan = MutableLiveData<List<Pengajuan>>()
    val dataPengajuan get() = _dataPengajuan

    fun getPengajuan(
        groupBy: String,
        bulan: String,
        tahun: String,
        type: String
    ){
        _dataPengajuan = RemoteRepository(apiInterface)
            .getPengajuan(groupBy ,bulan, tahun, type)
    }

}