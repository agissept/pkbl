package id.agis.pkbl.data.source.remote

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.agis.pkbl.constant.Constant
import id.agis.pkbl.data.source.remote.retrofit.ApiClient
import id.agis.pkbl.data.source.remote.retrofit.ApiInterface
import id.agis.pkbl.model.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteRepository(val context: Context) {
    companion object {
        fun getInstance(context: Context): RemoteRepository {
            var instance: RemoteRepository? = null
            if (instance == null) {
                synchronized(RemoteRepository::class.java) {
                    if (instance == null) {
                        instance = RemoteRepository(context)
                    }
                }
            }
            return instance as RemoteRepository
        }
    }

    private val apiInterface: ApiInterface = ApiClient.retrofit(getToken(context)).create(
        ApiInterface::class.java
    )

    private fun getToken(context: Context): String? {
        val sharedPreferences =
            context.getSharedPreferences(Constant.LOGIN_STATUS, Context.MODE_PRIVATE)

        return sharedPreferences.getString(Constant.USER_TOKEN, null)
    }


    fun getBinaLingkungan(): LiveData<List<Pemohon>> {
        val dataBinaLingkungan = MutableLiveData<List<Pemohon>>()

        val call: Call<BinaLingkunganResponse> = apiInterface.getBinaLingkungan()
        call.enqueue(object : Callback<BinaLingkunganResponse> {
            override fun onFailure(call: Call<BinaLingkunganResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<BinaLingkunganResponse>,
                response: Response<BinaLingkunganResponse>
            ) {
                dataBinaLingkungan.postValue(response.body()?.pemohon)
            }

        })

        return dataBinaLingkungan
    }

    fun getKemitraan(): LiveData<List<Pemohon>> {
        val dataKemitraan = MutableLiveData<List<Pemohon>>()

        val call: Call<KemitraanResponse> = apiInterface.getKemitraan()
        call.enqueue(object : Callback<KemitraanResponse> {
            override fun onFailure(call: Call<KemitraanResponse>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<KemitraanResponse>,
                response: Response<KemitraanResponse>
            ) {
                dataKemitraan.postValue(response.body()?.pemohon)
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

    fun uploadImage(file: MultipartBody.Part, idPemohon: Int) {
        val call = apiInterface.uploadFiles(file, idPemohon)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                println("error $t aaaaaaaaaaaaaa")
            }

            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {

            }
        })
    }
}
