package id.agis.pkbl.di

import android.content.Context
import id.agis.pkbl.data.PKBLRepository
import id.agis.pkbl.data.local.LocalRepository
import id.agis.pkbl.data.remote.RemoteRepository
import id.agis.pkbl.data.remote.retrofit.ApiClient
import id.agis.pkbl.data.remote.retrofit.ApiInterface
import id.agis.pkbl.util.LiveDataUtil.appDao
import io.realm.Realm
import io.realm.RealmConfiguration


class Injection {
    companion object{
        fun provideRepository(context: Context, token: String?): PKBLRepository {
            val apiInterface: ApiInterface = ApiClient.retrofit(token).create(ApiInterface::class.java)

            Realm.init(context)
            val configuration = RealmConfiguration.Builder().build()
            val db = Realm.getInstance(configuration)
            val localRepository = LocalRepository.getInstance(db.appDao())
            val remoteRepository = RemoteRepository(apiInterface)

            return PKBLRepository.getInstance(localRepository, remoteRepository)
        }
    }


}