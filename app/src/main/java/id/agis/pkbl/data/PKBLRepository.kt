package id.agis.pkbl.data

import androidx.lifecycle.LiveData
import id.agis.pkbl.data.local.LocalRepository
import id.agis.pkbl.data.local.entities.PemohonEntity
import id.agis.pkbl.data.remote.ApiResponse
import id.agis.pkbl.data.remote.RemoteRepository
import id.agis.pkbl.model.Pemohon
import id.agis.pkbl.model.User
import id.agis.pkbl.util.LiveRealmData
import id.agis.pkbl.vo.Resource
import io.realm.RealmResults
import okhttp3.MultipartBody
import java.io.IOException

class PKBLRepository(
    val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) : PKBLDataSource {
    companion object {
        @Volatile
        private var instance: PKBLRepository? = null

        fun getInstance(
            localRepository: LocalRepository,
            remoteData: RemoteRepository
        ): PKBLRepository {
            if (instance == null) {
                synchronized(PKBLRepository::class.java) {
                    if (instance == null) {
                        instance =
                            PKBLRepository(localRepository, remoteData)
                    }
                }
            }
            return instance!!
        }
    }


    override fun getAllKemitraan(): LiveData<Resource<RealmResults<PemohonEntity>>> {
        return object : NetworkBoundResource<PemohonEntity, List<Pemohon>>() {
            override fun loadFromDB(): LiveRealmData<PemohonEntity> {
                return localRepository.getAllKemitraan()
            }

            override fun shouldFetch(data: RealmResults<PemohonEntity>?): Boolean {
                return (data == null) || (data.isEmpty()) && isConnectionAvailable()
            }

            override fun createCall(): LiveData<ApiResponse<List<Pemohon>>>? {
                return remoteRepository.getKemitraan()
            }

            override fun saveCallResult(data: List<Pemohon>) {

                val pemohonEntity = ArrayList<PemohonEntity>()
                data.forEach {
                    pemohonEntity.add(
                        PemohonEntity(
                            it.idPemohon,
                            it.namaLengkap,
                            it.nilaiPengajuan

                        )
                    )
                }
                localRepository.insertAllKemitraan(pemohonEntity)
            }

        }.asLiveData()

    }

    override fun getAllBinaLingkungan(): LiveData<Resource<RealmResults<PemohonEntity>>> {
        return object : NetworkBoundResource<PemohonEntity, List<Pemohon>>() {
            override fun loadFromDB(): LiveRealmData<PemohonEntity> {
                return localRepository.getAllBinaLingkungan()
            }

            override fun shouldFetch(data: RealmResults<PemohonEntity>?): Boolean {
                return (data == null) || (data.isEmpty()) && isConnectionAvailable()
            }

            override fun createCall(): LiveData<ApiResponse<List<Pemohon>>>? {
                return remoteRepository.getBinaLingkungan()
            }

            override fun saveCallResult(data: List<Pemohon>) {
                val pemohonEntity = ArrayList<PemohonEntity>()
                data.forEach {
                    pemohonEntity.add(
                        PemohonEntity(
                            it.idPemohon,
                            it.namaLengkap,
                            it.nilaiPengajuan
                        )
                    )
                }
                localRepository.insertAllBinaLingkungan(pemohonEntity)
            }

        }.asLiveData()
    }

    override fun loginRequest(email: String, pass: String): LiveData<User> {
        return remoteRepository.loginRequest(email, pass)
    }

    override fun uploadFile(multipartBody: MultipartBody.Part, idPemohon: Int) {
        remoteRepository.uploadFile(multipartBody, idPemohon)
    }

    fun isConnectionAvailable(): Boolean {
        val runtime = Runtime.getRuntime()
        try {
            val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
            val exitValue = ipProcess.waitFor()
            return exitValue == 0
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return false
    }
}