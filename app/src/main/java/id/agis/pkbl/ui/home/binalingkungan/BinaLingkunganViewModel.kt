package id.agis.pkbl.ui.home.binalingkungan

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.agis.pkbl.data.PKBLRepository
import id.agis.pkbl.data.local.entities.PemohonEntity
import id.agis.pkbl.vo.Resource
import io.realm.RealmResults

class BinaLingkunganViewModel(private val pkblRepository: PKBLRepository): ViewModel() {
    val data: LiveData<Resource<RealmResults<PemohonEntity>>>  get() = pkblRepository.getAllBinaLingkungan()
}