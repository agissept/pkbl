package id.agis.pkbl.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.agis.pkbl.constant.Constant
import id.agis.pkbl.data.PKBLRepository
import id.agis.pkbl.di.Injection
import id.agis.pkbl.ui.detail.binalingkungan.DetailBinaLingkunganViewModel
import id.agis.pkbl.ui.home.binalingkungan.BinaLingkunganViewModel
import id.agis.pkbl.ui.home.kemitraan.KemitraanViewModel
import id.agis.pkbl.ui.login.LoginViewModel


class ViewModelFactory(
    private val pkblRepository: PKBLRepository
) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            val token = getToken(context)
            if (token != null) {
                synchronized(ViewModelFactory::class.java) {
                    if (instance == null) {
                        instance = ViewModelFactory(Injection.provideRepository(context, token))
                    }
                }
            } else {
                instance = ViewModelFactory(Injection.provideRepository(context, null))
            }
            return instance!!
        }

        private fun getToken(context: Context): String? {
            val sharedPreferences =
                context.getSharedPreferences(Constant.LOGIN_STATUS, Context.MODE_PRIVATE)

            return sharedPreferences.getString(Constant.USER_TOKEN, null)
        }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                return LoginViewModel( pkblRepository) as T
            }

            modelClass.isAssignableFrom(KemitraanViewModel::class.java) -> {
                return KemitraanViewModel(pkblRepository) as T
            }
            modelClass.isAssignableFrom(BinaLingkunganViewModel::class.java) -> {
                return BinaLingkunganViewModel(pkblRepository) as T
            }
            modelClass.isAssignableFrom(DetailBinaLingkunganViewModel::class.java) -> {
                return DetailBinaLingkunganViewModel(pkblRepository) as T
            }

        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}