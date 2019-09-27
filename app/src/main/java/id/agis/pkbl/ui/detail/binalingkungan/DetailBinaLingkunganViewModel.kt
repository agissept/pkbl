package id.agis.pkbl.ui.detail.binalingkungan

import androidx.lifecycle.ViewModel
import id.agis.pkbl.data.source.remote.RemoteRepository
import okhttp3.MultipartBody

class DetailBinaLingkunganViewModel : ViewModel() {
 fun uploadImage(imageName: MultipartBody.Part) {
   RemoteRepository().uploadImage(imageName)
 }
}