package id.agis.pkbl.data

import id.agis.pkbl.model.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @GET("result_pengajuan.php")
    fun getPengajuan(
        @Query("groupBy") groupBy: String,
        @Query("bulan") bulan: String,
        @Query("tahun") tahun: String,
        @Query("type") type: String
    ): Call<List<Pengajuan>>

    @GET("penugasan.php")
    fun getPenugasan(
        @Query("type") type: String,
        @Query("user") user: String
    ): Call<List<Pengajuan>>

    @GET("search.php")
    fun getSearch(
        @Query("query") query: String,
        @Query("bulan") bulan: String,
        @Query("tahun") tahun: String
    ): Call<List<Pengajuan>>

    @FormUrlEncoded
    @POST("post_penugasan.php")
    fun postPenugasan(
        @Query("id") id: String,
        @Field("koordinat") koordinat: String
    ): Call<Status>

    @FormUrlEncoded
    @POST("post_penilaian.php")
    fun postPenilaian(
        @Query("id") id: String,
        @Field("penilai") penilai: String,
        @Field("penilaian") penilaian: String
    ): Call<Status>

    @FormUrlEncoded
    @POST("post_persetujuan.php")
    fun postPersetujuan(
        @Query("id") id: String,
        @Field("penyetuju") penyetuju: String,
        @Field("status_persetujuan") statusPersetujuan: Boolean,
        @Field("alasan") alasan: String
    ): Call<Status>

    @FormUrlEncoded
    @POST("post_pencairan.php")
    fun postPencairan(
        @Query("id") id: String,
        @Field("bendahara") bendahara: String
    ): Call<Status>

    @FormUrlEncoded
    @POST("login.php")
    fun loginRequest(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<User>

    @Multipart
    @POST("postfile.php")
    fun uploadFiles(@Part file: MultipartBody.Part, @Part("id") idPemohon: Int): Call<List<UploadFileResponse>>

    @GET("result_file.php")
    fun getFiles(@Query("id") id: String): Call<List<UploadFileResponse>>

    @Streaming
    @GET("file/{id}/{file}")
    fun downloadFile(@Path("id") id:String,
                     @Path("file") url:String): Call<ResponseBody>

    @GET("result_info.php")
    fun getInfo(): Call<List<Info>>
}