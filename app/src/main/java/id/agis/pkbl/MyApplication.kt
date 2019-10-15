package id.agis.pkbl

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        val configuration = RealmConfiguration.Builder()
            .name("pkbl.db")
            .schemaVersion(0)
            .build()
        Realm.setDefaultConfiguration(configuration)
    }
}