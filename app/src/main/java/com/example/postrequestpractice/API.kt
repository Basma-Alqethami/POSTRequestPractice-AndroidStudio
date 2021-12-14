package com.example.postrequestpractice

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface API {
    @GET("test/")
    fun getData(): Call<data>

    @POST("test/")
    fun postData(@Body userData: dataItem): Call<dataItem>
}