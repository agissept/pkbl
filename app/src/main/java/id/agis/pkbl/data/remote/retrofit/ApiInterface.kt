package id.agis.pkbl.data.remote.retrofit

import id.agis.pkbl.model.BinaLingkunganResponse
import id.agis.pkbl.model.KemitraanResponse
import id.agis.pkbl.model.UserResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @GET("pemohon")
    fun getBinaLingkungan(): Call<BinaLingkunganResponse>

    @GET("pemohon")
    fun getKemitraan(): Call<KemitraanResponse>

    @FormUrlEncoded
    @POST("user/login")
    fun loginRequest(@Field("username") username: String, @Field("password") pass: String): Call<UserResponse>

    @Multipart
    @POST("pemohon/files")
    fun uploadFiles(@Part file: MultipartBody.Part, @Part("id_pemohon") idPemohon: Int): Call<ResponseBody>
}