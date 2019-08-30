package id.agis.pkbl.data.source.remote.retrofit

import id.agis.pkbl.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {

//    private val client: OkHttpClient = OkHttpClient.Builder()
//        .addInterceptor { chain ->
//            var request = chain.request()
//            val url = request.url()
//                .newBuilder()
//                .addQueryParameter("api_key", BuildConfig.API_KEY)
//                .build()
//            request = request
//                .newBuilder()
//                .url(url)
//                .build()
//            chain.proceed(request)
//        }.build()


    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
//        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}
