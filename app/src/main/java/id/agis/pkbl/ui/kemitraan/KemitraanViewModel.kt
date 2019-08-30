package id.agis.pkbl.ui.kemitraan

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.agis.pkbl.model.Kemitraan
import id.agis.pkbl.data.source.remote.RemoteRepository

class KemitraanViewModel : ViewModel() {
    val data: LiveData<List<Kemitraan>> = RemoteRepository().getKemitraan()
}