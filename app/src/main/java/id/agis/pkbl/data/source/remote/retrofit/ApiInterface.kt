package id.agis.pkbl.data.source.remote.retrofit

import id.agis.pkbl.model.BinaLingkugan
import id.agis.pkbl.model.KemitraanResponse
import id.agis.pkbl.model.UserResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @GET("posts")
    fun getBinaLingkungan(): Call<List<BinaLingkugan>>

    @GET("pemohon")
    fun getKemitraan(): Call<KemitraanResponse>

    @FormUrlEncoded
    @POST("user/login")
    fun loginRequest(@Field("username") username: String, @Field("password") pass: String): Call<UserResponse>

    @Multipart
    @POST("upload.php")
    fun uploadImage(imageName: MultipartBody.Part): Call<ResponseBody>
}