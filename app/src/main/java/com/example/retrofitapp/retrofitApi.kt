package com.example.retrofitapp

import android.provider.SyncStateContract
import com.example.retrofitapp.Constants.Companion.baseUrl
import com.example.retrofitapp.Constants.Companion.baseurl
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface retrofitApi {
//    const val baseUrl: String = "https://api.unsplash.com/"

    @GET("/photos")
    fun getImages(@Query("client_id") clientId: String, @Query("page") page: Int): Response<List<item_data>>
    companion object{
        fun create(): retrofitApi {
            val client = OkHttpClient.Builder()
                .addInterceptor(OkHttpProfilerInterceptor())
                .build()

            return Retrofit.Builder()
                .baseUrl( "https://api.unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(retrofitApi::class.java)
        }
    }



}