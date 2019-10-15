package id.agis.pkbl.data.local.realm

import id.agis.pkbl.data.local.entities.PemohonEntity
import id.agis.pkbl.util.LiveDataUtil.asLiveData
import id.agis.pkbl.util.LiveRealmData
import io.realm.Realm

class AppDao(private val realm: Realm) {
    fun insertAllKemitraan(kemitraan: List<PemohonEntity>) {
        realm.beginTransaction()
        realm.copyToRealm(kemitraan)
        realm.commitTransaction()
    }

    fun getAllKemitraan(): LiveRealmData<PemohonEntity> {
        return realm.where(PemohonEntity::class.java).findAllAsync().asLiveData()
    }

    fun truncateKemitraan() {
        realm.where(PemohonEntity::class.java)
        realm.beginTransaction()
        realm.deleteAll()
        realm.commitTransaction()
    }

    fun insertAllBinaLingkungan(binaLingkungan: List<PemohonEntity>) {
        realm.beginTransaction()
        realm.copyToRealm(binaLingkungan)
        realm.commitTransaction()
    }

    fun getAllBinaLingkungan(): LiveRealmData<PemohonEntity> {
        return realm.where(PemohonEntity::class.java).findAllAsync().asLiveData()
    }

    fun truncateBinaLingkungan() {
        realm.where(PemohonEntity::class.java)
        realm.beginTransaction()
        realm.deleteAll()
        realm.commitTransaction()
    }
}