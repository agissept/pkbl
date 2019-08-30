package id.agis.pkbl.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.agis.pkbl.data.source.remote.retrofit.ApiClient
import id.agis.pkbl.data.source.remote.retrofit.ApiInterface
import id.agis.pkbl.model.BinaLingkugan
import id.agis.pkbl.model.Kemitraan
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteRepository {
    private val apiInterface: ApiInterface = ApiClient.retrofit.create(
        ApiInterface::class.java)

    fun getBinaLingkungan(): LiveData<List<BinaLingkugan>> {
        val dataBinaLingkungan = MutableLiveData<List<BinaLingkugan>>()

        val call: Call<List<BinaLingkugan>> = apiInterface.getBinaLingkungan()
        call.enqueue(object : Callback<List<BinaLingkugan>> {
            override fun onFailure(call: Call<List<BinaLingkugan>>, t: Throwable) {

            }

            override fun onResponse(call: Call<List<BinaLingkugan>>, response: Response<List<BinaLingkugan>>) {
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

            override fun onResponse(call: Call<List<Kemitraan>>, response: Response<List<Kemitraan>>) {
                dataKemitraan.postValue(response.body())
            }

        })

        return dataKemitraan
    }
}