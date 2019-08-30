package id.agis.pkbl.data.source.remote.retrofit

import id.agis.pkbl.model.BinaLingkugan
import id.agis.pkbl.model.Kemitraan
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("posts")
    fun getBinaLingkungan(): Call<List<BinaLingkugan>>

    @GET("posts")
    fun getKemitraan(): Call<List<Kemitraan>>
}