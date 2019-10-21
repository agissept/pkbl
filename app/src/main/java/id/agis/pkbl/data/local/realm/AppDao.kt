package id.agis.pkbl.data.local.realm

import id.agis.pkbl.data.local.entities.FileEntity
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


    fun insertFile(file: FileEntity) {
        realm.beginTransaction()
        realm.copyToRealm(file)
        realm.commitTransaction()
    }

    fun deleteFile(id: Long) {
        val dataResults = realm.where(FileEntity::class.java).equalTo("id", id).findFirst()
        realm.beginTransaction()
        dataResults?.deleteFromRealm()
        realm.commitTransaction()
    }

    fun getAllFile(idPemohon: Int): LiveRealmData<FileEntity> {
        return realm.where(FileEntity::class.java).findAllAsync().asLiveData()
    }
}