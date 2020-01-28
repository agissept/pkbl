package id.agis.pkbl.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.agis.pkbl.model.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteRepository {
    private val apiInterface = ApiClient.retrofit().create(ApiInterface::class.java)

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

    fun uploadFile(file: MultipartBody.Part, idPemohon: String): MutableLiveData<UploadFileResponse> {
        val uploadResponse = MutableLiveData<UploadFileResponse>()

        val call = apiInterface.uploadFiles(file, idPemohon.toInt())
        call.enqueue(object : Callback<List<UploadFileResponse>> {
            override fun onFailure(call: Call<List<UploadFileResponse>>, t: Throwable) {
                println("$t aaaaaaaaaaaaaaaaaaaaaa")
            }

            override fun onResponse(
                call: Call<List<UploadFileResponse>>,
                response: Response<List<UploadFileResponse>>
            ) {
                uploadResponse.postValue(response.body()!![0])
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
        query: String,
        bulan: String,
        tahun: String
    ): MutableLiveData<List<Pengajuan>> {
        val dataPenugasan = MutableLiveData<List<Pengajuan>>()

        val call: Call<List<Pengajuan>> = apiInterface.getSearch(query, bulan, tahun)
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

    fun postPenugasan(id: String, koordinat: String): MutableLiveData<Status> {
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

    fun postPenilaian(id: String, user: String, penilaian: String): MutableLiveData<Status> {
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

    fun postPersetujuan(
        id: String,
        user: String,
        statusPersetujuan: Boolean,
        alasan: String
    ): MutableLiveData<Status> {
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

    fun postPencairan(id: String, user: String): MutableLiveData<Status> {
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

    fun getFiles(id: String): MutableLiveData<List<UploadFileResponse>> {
        val listFile = MutableLiveData<List<UploadFileResponse>>()

        val call: Call<List<UploadFileResponse>> = apiInterface.getFiles(id)
        call.enqueue(object : Callback<List<UploadFileResponse>> {
            override fun onFailure(call: Call<List<UploadFileResponse>>, t: Throwable) {
                println("aaaaaaaaaaaaa $t")
            }

            override fun onResponse(
                call: Call<List<UploadFileResponse>>,
                response: Response<List<UploadFileResponse>>
            ) {
                listFile.postValue(response.body())
            }

        })

        return listFile
    }

    fun downloadFile(id: String, name: String): MutableLiveData<ResponseBody> {
        val responseBody = MutableLiveData<ResponseBody>()
        val call = apiInterface.downloadFile(id,name)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                responseBody.postValue(response.body())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                println("aaaaaaaaaaaaa $t")
            }
        })

        return responseBody
    }


    fun getInfo(): MutableLiveData<List<Info>> {
        val listInfo = MutableLiveData<List<Info>>()
        val call = apiInterface.getInfo()

        call.enqueue(object : Callback<List<Info>> {
            override fun onResponse(call: Call<List<Info>>, response: Response<List<Info>>) {
                listInfo.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Info>>, t: Throwable) {
                println("aaaaaaaaaaaaa $t")
            }
        })

        return listInfo
    }

}
