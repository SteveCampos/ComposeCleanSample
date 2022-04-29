package com.stevecampos.composecleansample.data.remote.api.base


import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class BaseApiClient<T>(private val classT: Class<T>) {
    companion object {
        const val URL_SERVER: String = "https://jsonplaceholder.typicode.com/"
        const val CONNECTION_TIMEOUT: Long = 180L
        const val READ_TIMEOUT: Long = 180L
        const val WRITE_TIMEOUT: Long = 180L
    }

    open fun getApiClient(okHttpClient: OkHttpClient = defaultOkHttpClient()): T {
        val retrofitBuilder = Retrofit.Builder().apply {
            baseUrl(URL_SERVER)
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create())
        }

        val retrofit = retrofitBuilder.build()
        return retrofit.create(classT)
    }

    private fun defaultOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }
}