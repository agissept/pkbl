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

    private val apiInterface: ApiInterface = ApiClient.retrofit().create(
        ApiInterface::class.java
    )

    fun getToken(context: Context): String? {
        val sharedPreferences =
            context.getSharedPreferences(Constant.LOGIN_STATUS, Context.MODE_PRIVATE)

        return sharedPreferences.getString(Constant.USER_TOKEN, null)
    }


    fun getBinaLingkungan(): LiveData<List<BinaLingkugan>> {
        val dataBinaLingkungan = MutableLiveData<List<BinaLingkugan>>()

        val call: Call<List<BinaLingkugan>> = apiInterface.getBinaLingkungan()
        call.enqueue(object : Callback<List<BinaLingkugan>> {
            override fun onFailure(call: Call<List<BinaLingkugan>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<List<BinaLingkugan>>,
                response: Response<List<BinaLingkugan>>
            ) {
                dataBinaLingkungan.postValue(response.body())
            }

        })

        return dataBinaLingkungan
    }

    fun getKemitraan(): LiveData<List<Kemitraan>> {
        val dataKemitraan = MutableLiveData<List<Kemitraan>>()

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

    fun uploadImage(imageName: MultipartBody.Part) {
        val call = apiInterface.uploadImage(imageName)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }
}
