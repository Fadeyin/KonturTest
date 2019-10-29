package com.example.fadeyin.konturtest.models

import retrofit2.Retrofit
import retrofit2.http.*
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

interface InterfaceAPI {

    @GET("generated-01.json")
    fun getUser1(
    ):Observable<ArrayList<User>>
    @GET("generated-02.json")
    fun getUser2(
    ):Observable<ArrayList<User>>
    @GET("generated-03.json")
    fun getUser3(
    ):Observable<ArrayList<User>>

    companion object Factory {
        val BASEURL = "https://raw.githubusercontent.com/SkbkonturMobile/mobile-test-droid/master/json/"
        fun createService(): InterfaceAPI {
            val httpClient = OkHttpClient.Builder()
            val client = httpClient.build()
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASEURL)
                .client(client)
                .build()
            return retrofit.create(InterfaceAPI::class.java)
        }
    }
}