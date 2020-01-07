package id.agis.pkbl.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.agis.pkbl.constant.Constant
import id.agis.pkbl.data.ApiInterface
import id.agis.pkbl.di.Injection
import id.agis.pkbl.ui.detail.binalingkungan.DetailPengajuanViewModel
import id.agis.pkbl.ui.home.HomeViewModel
import id.agis.pkbl.ui.login.LoginViewModel
import id.agis.pkbl.ui.search.SearchViewModel


class ViewModelFactory(
    private val apiInterface: ApiInterface
) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory {
                synchronized(ViewModelFactory::class.java) {
                    if (instance == null) {
                        instance = ViewModelFactory(Injection.provideRepository())
                    }
                }
            return instance!!
        }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                return LoginViewModel(apiInterface) as T
            }

            modelClass.isAssignableFrom(DetailPengajuanViewModel::class.java) -> {
                return DetailPengajuanViewModel(apiInterface) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                return HomeViewModel(apiInterface) as T
            }
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                return SearchViewModel(apiInterface) as T
            }

        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}