package id.agis.pkbl.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.agis.pkbl.data.RemoteRepository
import id.agis.pkbl.data.ApiClient
import id.agis.pkbl.data.ApiInterface
import id.agis.pkbl.model.Pengajuan

class DashboardViewModel: ViewModel() {
    private val apiInterface: ApiInterface = ApiClient.retrofit().create(
        ApiInterface::class.java)
    private var _tahun = MutableLiveData<List<Pengajuan>>()
    val tahun get() = _tahun
    private var _bulan = MutableLiveData<List<Pengajuan>>()
    val bulan get() = _bulan

    fun getTahun(){
        _tahun = RemoteRepository(apiInterface)
            .getPengajuan("tahun", "", "", "")
    }

    fun getBulan(tahun: String){
        _bulan = RemoteRepository(apiInterface)
            .getPengajuan("bulan", "", tahun, "")
    }
}