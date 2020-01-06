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

    @FormUrlEncoded
    @POST("post_penugasan.php")
    fun postPenugasan(
        @Query("id") id: Int,
        @Field("koordinat") koordinat: String
    ): Call<Status>

    @FormUrlEncoded
    @POST("post_penilaian.php")
    fun postPenilaian(
        @Query("id") id: Int,
        @Field("penilai") penilai: String,
        @Field("penilaian") penilaian: String
    ): Call<Status>

    @FormUrlEncoded
    @POST("post_persetujuan.php")
    fun postPersetujuan(
        @Query("id") id: Int,
        @Field("penyetuju") penyetuju: String,
        @Field("status_persetujuan") statusPersetujuan: Boolean,
        @Field("alasan") alasan: String
    ): Call<Status>

    @FormUrlEncoded
    @POST("post_pencairan.php")
    fun postPencairan(
        @Query("id") id: Int,
        @Field("pencair") pencair: String
    ): Call<Status>

    @FormUrlEncoded
    @POST("login.php")
    fun loginRequest(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<User>

    @Multipart
    @POST("pemohon/files")
    fun uploadFiles(@Part file: MultipartBody.Part, @Part("id_pemohon") idPemohon: Int): Call<UploadFileResponse>
}