package id.agis.pkbl.di

import id.agis.pkbl.data.ApiClient
import id.agis.pkbl.data.ApiInterface

class Injection {
    companion object{
        fun provideRepository(): ApiInterface {
            return ApiClient.retrofit().create(
                ApiInterface::class.java)
        }
    }


}