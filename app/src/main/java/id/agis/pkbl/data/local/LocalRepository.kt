package id.agis.pkbl.data.local

import id.agis.pkbl.data.local.entities.FileEntity
import id.agis.pkbl.data.local.entities.PemohonEntity
import id.agis.pkbl.data.local.realm.AppDao
import id.agis.pkbl.util.LiveRealmData


class LocalRepository(private val appDao: AppDao) {
    companion object {
        private var instance: LocalRepository? = null
        fun getInstance(appDao: AppDao): LocalRepository {
            if (instance == null) {
                instance =
                    LocalRepository(appDao)
            }
            return instance!!
        }
    }

    fun insertAllKemitraan(kemitraan: List<PemohonEntity>){
        appDao.truncateKemitraan()
        appDao.insertAllKemitraan(kemitraan)

    }

    fun getAllKemitraan(): LiveRealmData<PemohonEntity> {
        return appDao.getAllKemitraan()
    }

    fun insertAllBinaLingkungan(binaLingkungan: List<PemohonEntity>){
//        appDao.truncateBinaLingkungan()
//        appDao.insertAllBinaLingkungan(binaLingkungan)

        appDao.truncateKemitraan()
        appDao.insertAllKemitraan(binaLingkungan)

    }

    fun getAllBinaLingkungan(): LiveRealmData<PemohonEntity> {
//        return appDao.getAllBinaLingkungan()
        return appDao.getAllKemitraan()
    }


    fun getAllFile(idPemohon: Int): LiveRealmData<FileEntity>{
        return appDao.getAllFile(idPemohon)
    }

    fun insertFile(file: FileEntity){
        appDao.insertFile(file)
    }

    fun deleteFile(id: Long){
        appDao.deleteFile(id)
    }

}