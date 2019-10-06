package id.agis.pkbl.ui.home.binalingkungan

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.agis.pkbl.data.source.remote.RemoteRepository
import id.agis.pkbl.model.Pemohon

class BinaLingkunganViewModel(remoteRepository: RemoteRepository): ViewModel() {
    val data: LiveData<List<Pemohon>> = remoteRepository.getBinaLingkungan()
}