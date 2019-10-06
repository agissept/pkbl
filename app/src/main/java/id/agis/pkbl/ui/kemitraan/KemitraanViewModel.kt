package id.agis.pkbl.ui.kemitraan

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.agis.pkbl.data.source.remote.RemoteRepository
import id.agis.pkbl.model.Kemitraan

class KemitraanViewModel(remoteRepository: RemoteRepository) : ViewModel() {
    val data: LiveData<List<Kemitraan>> = remoteRepository.getKemitraan()
}