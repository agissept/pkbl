package id.agis.pkbl.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.agis.pkbl.data.source.remote.RemoteRepository
import id.agis.pkbl.ui.home.binalingkungan.BinaLingkunganViewModel
import id.agis.pkbl.ui.home.kemitraan.KemitraanViewModel
import id.agis.pkbl.ui.login.LoginViewModel


class ViewModelFactory(
    private val remoteRepository: RemoteRepository
) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            synchronized(ViewModelFactory::class.java) {
                if (instance == null) {
                    instance = ViewModelFactory(RemoteRepository.getInstance(context))
                }
            }
            return instance!!
        }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                return LoginViewModel(remoteRepository) as T
            }

            modelClass.isAssignableFrom(KemitraanViewModel::class.java) -> {
                return KemitraanViewModel(remoteRepository) as T
            }
            modelClass.isAssignableFrom(BinaLingkunganViewModel::class.java) -> {
                return BinaLingkunganViewModel(remoteRepository) as T
            }

        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}