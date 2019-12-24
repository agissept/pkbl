package id.agis.pkbl.ui.dashboard.persektor

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.agis.pkbl.data.remote.RemoteRepository
import id.agis.pkbl.data.remote.retrofit.ApiClient
import id.agis.pkbl.data.remote.retrofit.ApiInterface
import id.agis.pkbl.model.Pengajuan

class PerSektorViewModel(): ViewModel() {
    val apiInterface: ApiInterface = ApiClient.retrofit().create(ApiInterface::class.java)
    val dataPengajuan: LiveData<List<Pengajuan>> get() = RemoteRepository(apiInterface).getPengajuan()
}