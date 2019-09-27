package id.agis.pkbl.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.agis.pkbl.data.source.remote.retrofit.ApiClient
import id.agis.pkbl.data.source.remote.retrofit.ApiInterface
import id.agis.pkbl.model.BinaLingkugan
import id.agis.pkbl.model.Kemitraan
import id.agis.pkbl.model.User
import id.agis.pkbl.model.UserResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteRepository {
    private val apiInterface: ApiInterface = ApiClient.retrofit.create(
        ApiInterface::class.java
    )

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

        val call: Call<List<Kemitraan>> = apiInterface.getKemitraan()
        call.enqueue(object : Callback<List<Kemitraan>> {
            override fun onFailure(call: Call<List<Kemitraan>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<List<Kemitraan>>,
                response: Response<List<Kemitraan>>
            ) {
                dataKemitraan.postValue(response.body())
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

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                loginStatus.postValue(response.body()?.user)
            }
        })
        return loginStatus
    }

    fun uploadImage(imageName: MultipartBody.Part) {
        val call = apiInterface.uploadImage(imageName)
       call.enqueue(object : Callback<ResponseBody>{
           override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
               TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
           }

           override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
               TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
           }
       })
    }
}