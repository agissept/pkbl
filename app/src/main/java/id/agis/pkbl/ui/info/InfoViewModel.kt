package id.agis.pkbl.ui.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.agis.pkbl.data.RemoteRepository
import id.agis.pkbl.model.Info

class InfoViewModel: ViewModel() {
    private var _listInfo = MutableLiveData<List<Info>>()
    val listInfo get() = _listInfo

    fun getInfo(){
        _listInfo = RemoteRepository().getInfo()
    }
}