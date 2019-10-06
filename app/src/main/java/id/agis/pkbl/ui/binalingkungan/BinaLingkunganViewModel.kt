package id.agis.pkbl.ui.binalingkungan

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.agis.pkbl.data.source.remote.RemoteRepository
import id.agis.pkbl.model.BinaLingkugan

class BinaLingkunganViewModel(remoteRepository: RemoteRepository): ViewModel() {
    val data: LiveData<List<BinaLingkugan>> = remoteRepository.getBinaLingkungan()
}