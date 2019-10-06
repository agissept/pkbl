package id.agis.pkbl.ui.home.kemitraan

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.agis.pkbl.data.source.remote.RemoteRepository
import id.agis.pkbl.model.Pemohon

class KemitraanViewModel(remoteRepository: RemoteRepository) : ViewModel() {
    val data: LiveData<List<Pemohon>> = remoteRepository.getKemitraan()
}