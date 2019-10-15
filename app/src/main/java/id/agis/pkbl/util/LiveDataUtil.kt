package id.agis.pkbl.util

import id.agis.pkbl.data.local.realm.AppDao
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmResults


object LiveDataUtil {
    fun Realm.appDao(): AppDao =
        AppDao(this)

    fun <T : RealmModel> RealmResults<T>.asLiveData() = LiveRealmData(this)
}