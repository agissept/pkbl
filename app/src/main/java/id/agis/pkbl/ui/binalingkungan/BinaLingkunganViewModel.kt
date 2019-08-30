package id.agis.pkbl.ui.binalingkungan

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.agis.pkbl.model.BinaLingkugan
import id.agis.pkbl.data.source.remote.RemoteRepository

class BinaLingkunganViewModel: ViewModel() {
    val data: LiveData<List<BinaLingkugan>> = RemoteRepository().getBinaLingkungan()
}