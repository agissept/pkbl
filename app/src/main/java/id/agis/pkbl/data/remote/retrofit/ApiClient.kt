package id.agis.pkbl.data.remote.retrofit

import id.agis.pkbl.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {

    private fun getClient(token: String): OkHttpClient =
        OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Auth", "Bearer $token")
                .build()
            chain.proceed(newRequest)
        }.build()


    fun retrofit(token: String? = null): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL).apply {
                token?.let { client(getClient(it)) }
            }
            .addConverterFactory(GsonConverterFactory.create())
            .build()


}
