package id.agis.pkbl.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.agis.pkbl.data.ApiInterface
import id.agis.pkbl.data.RemoteRepository
import id.agis.pkbl.model.Pengajuan

class SearchViewModel(val apiInterface: ApiInterface): ViewModel() {
    private var _listPengajuan = MutableLiveData<List<Pengajuan>>()
    val listPengajuan get() = _listPengajuan

    fun getSearch(query: String){
        _listPengajuan = RemoteRepository(apiInterface).getSearch(query)
    }
}