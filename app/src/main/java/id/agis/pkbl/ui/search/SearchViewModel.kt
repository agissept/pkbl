package id.agis.pkbl.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.agis.pkbl.data.RemoteRepository
import id.agis.pkbl.model.Pengajuan

class SearchViewModel : ViewModel() {
    private var _listPengajuan = MutableLiveData<List<Pengajuan>>()
    val listPengajuan get() = _listPengajuan
    private var _tahun = MutableLiveData<List<Pengajuan>>()
    val tahun get() = _tahun
    private var _bulan = MutableLiveData<List<Pengajuan>>()
    val bulan get() = _bulan

    fun getTahun() {
        _tahun = RemoteRepository().getPengajuan("tahun", "", "", "")
    }

    fun getBulan(tahun: String) {
        _bulan = RemoteRepository().getPengajuan("bulan", "", tahun, "")
    }

    fun getSearch(query: String, bulan: String, tahun: String) {
        _listPengajuan = RemoteRepository().getSearch(query, bulan, tahun)
    }
}