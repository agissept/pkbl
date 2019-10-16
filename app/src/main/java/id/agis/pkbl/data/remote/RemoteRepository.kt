package id.agis.pkbl.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.agis.pkbl.data.remote.retrofit.ApiInterface
import id.agis.pkbl.model.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteRepository(private val apiInterface: ApiInterface) {

    fun getBinaLingkungan(): LiveData<ApiResponse<List<Pemohon>>> {
        val dataBinaLingkungan = MutableLiveData<ApiResponse<List<Pemohon>>>()

        val call: Call<BinaLingkunganResponse> = apiInterface.getBinaLingkungan()
        call.enqueue(object : Callback<BinaLingkunganResponse> {
            override fun onFailure(call: Call<BinaLingkunganResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<BinaLingkunganResponse>,
                response: Response<BinaLingkunganResponse>
            ) {
                dataBinaLingkungan.postValue(
                    ApiResponse.success(
                        response.body()?.pemohon!!
                    )
                )
            }

        })

        return dataBinaLingkungan
    }

    fun getKemitraan(): LiveData<ApiResponse<List<Pemohon>>> {
        val dataKemitraan = MutableLiveData<ApiResponse<List<Pemohon>>>()

        val call: Call<KemitraanResponse> = apiInterface.getKemitraan()
        call.enqueue(object : Callback<KemitraanResponse> {
            override fun onFailure(call: Call<KemitraanResponse>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<KemitraanResponse>,
                response: Response<KemitraanResponse>
            ) {
                dataKemitraan.postValue(
                    ApiResponse.success(
                        response.body()?.pemohon!!
                    )
                )
            }

        })

        return dataKemitraan
    }

    fun loginRequest(email: String, pass: String): LiveData<User> {
        val loginStatus = MutableLiveData<User>()
        val call: Call<UserResponse> = apiInterface.loginRequest(email, pass)
        call.enqueue(object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                loginStatus.postValue(response.body()?.user)
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
}
