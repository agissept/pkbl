package id.agis.pkbl.data.remote.retrofit

import id.agis.pkbl.model.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @GET("pemohon")
    fun getBinaLingkungan(): Call<BinaLingkunganResponse>

    @GET("pemohon")
    fun getKemitraan(): Call<KemitraanResponse>

    @GET("result_pengajuan.php?orderBy=sektor")
    fun getPengajuan(): Call<List<Pengajuan>>

    @FormUrlEncoded
    @POST("user/login")
    fun loginRequest(@Field("username") username: String, @Field("password") pass: String): Call<UserResponse>

    @Multipart
    @POST("pemohon/files")
    fun uploadFiles(@Part file: MultipartBody.Part, @Part("id_pemohon") idPemohon: Int): Call<UploadFileResponse>
}