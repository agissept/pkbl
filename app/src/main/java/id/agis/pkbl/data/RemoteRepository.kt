package id.agis.pkbl.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.agis.pkbl.model.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteRepository(private val apiInterface: ApiInterface) {

    fun postLogin(email: String, password: String): LiveData<User> {
        val loginStatus = MutableLiveData<User>()
        val call: Call<User> = apiInterface.loginRequest(email, password)
        call.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                loginStatus.postValue(response.body())
            }
        })
        return loginStatus
    }

    fun uploadFile(file: MultipartBody.Part, idPemohon: Int): MutableLiveData<UploadFileResponse> {
        val uploadResponse = MutableLiveData<UploadFileResponse>()

        val call = apiInterface.uploadFiles(file, idPemohon)
        call.enqueue(object : Callback<UploadFileResponse> {
            override fun onFailure(call: Call<UploadFileResponse>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<UploadFileResponse>,
                response: Response<UploadFileResponse>
            ) {
                uploadResponse.postValue(response.body())
            }
        })

        return uploadResponse
    }

    fun getPengajuan(
        groupBy: String,
        bulan: String,
        tahun: String,
        type: String
    ): MutableLiveData<List<Pengajuan>> {
        val dataPengajuan = MutableLiveData<List<Pengajuan>>()

        val call: Call<List<Pengajuan>> = apiInterface.getPengajuan(groupBy, bulan, tahun, type)
        call.enqueue(object : Callback<List<Pengajuan>> {
            override fun onFailure(call: Call<List<Pengajuan>>, t: Throwable) {

                println("aaaaaaaaaaaaa $t")
            }

            override fun onResponse(
                call: Call<List<Pengajuan>>,
                response: Response<List<Pengajuan>>
            ) {
                dataPengajuan.postValue(response.body())
            }

        })

        return dataPengajuan
    }


    fun getPenugasan(
        type: String,
        user: String
    ): MutableLiveData<List<Pengajuan>> {
        val dataPenugasan = MutableLiveData<List<Pengajuan>>()

        val call: Call<List<Pengajuan>> = apiInterface.getPenugasan(type, user)
        call.enqueue(object : Callback<List<Pengajuan>> {
            override fun onFailure(call: Call<List<Pengajuan>>, t: Throwable) {
                println("aaaaaaaaaaaaa $t")
            }

            override fun onResponse(
                call: Call<List<Pengajuan>>,
                response: Response<List<Pengajuan>>
            ) {
                dataPenugasan.postValue(response.body())
            }

        })

        return dataPenugasan
    }

    fun getSearch(
        query: String
    ): MutableLiveData<List<Pengajuan>> {
        val dataPenugasan = MutableLiveData<List<Pengajuan>>()

        val call: Call<List<Pengajuan>> = apiInterface.getSearch(query)
        call.enqueue(object : Callback<List<Pengajuan>> {
            override fun onFailure(call: Call<List<Pengajuan>>, t: Throwable) {
                println("aaaaaaaaaaaaa $t")
            }

            override fun onResponse(
                call: Call<List<Pengajuan>>,
                response: Response<List<Pengajuan>>
            ) {
                dataPenugasan.postValue(response.body())
            }

        })

        return dataPenugasan
    }

    fun postPenugasan(id: Int, koordinat: String): MutableLiveData<Status> {
        val status = MutableLiveData<Status>()

        val call: Call<Status> = apiInterface.postPenugasan(id, koordinat)
        call.enqueue(object : Callback<Status> {
            override fun onFailure(call: Call<Status>, t: Throwable) {
                println("aaaaaaaaaaaaa $t")
            }

            override fun onResponse(
                call: Call<Status>,
                response: Response<Status>
            ) {
                status.postValue(response.body())
            }

        })

        return status
    }

    fun postPenilaian(id: Int, user: String, penilaian: String): MutableLiveData<Status> {
        val status = MutableLiveData<Status>()

        val call: Call<Status> = apiInterface.postPenilaian(id, user, penilaian)
        call.enqueue(object : Callback<Status> {
            override fun onFailure(call: Call<Status>, t: Throwable) {
                println("aaaaaaaaaaaaa $t")
            }

            override fun onResponse(
                call: Call<Status>,
                response: Response<Status>
            ) {
                status.postValue(response.body())
            }

        })

        return status
    }

    fun postPersetujuan(id: Int, user: String, statusPersetujuan: Boolean, alasan: String): MutableLiveData<Status> {
        val status = MutableLiveData<Status>()

        val call: Call<Status> = apiInterface.postPersetujuan(id, user, statusPersetujuan, alasan)
        call.enqueue(object : Callback<Status> {
            override fun onFailure(call: Call<Status>, t: Throwable) {
                println("aaaaaaaaaaaaa $t")
            }

            override fun onResponse(
                call: Call<Status>,
                response: Response<Status>
            ) {
                status.postValue(response.body())
            }

        })

        return status
    }

    fun postPencairan(id: Int, user: String): MutableLiveData<Status> {
        val status = MutableLiveData<Status>()

        val call: Call<Status> = apiInterface.postPencairan(id, user)
        call.enqueue(object : Callback<Status> {
            override fun onFailure(call: Call<Status>, t: Throwable) {
                println("aaaaaaaaaaaaa $t")
            }

            override fun onResponse(
                call: Call<Status>,
                response: Response<Status>
            ) {
                status.postValue(response.body())
            }

        })

        return status
    }

}
