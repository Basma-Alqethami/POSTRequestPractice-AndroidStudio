package com.example.postrequestpractice

import retrofit2.Call
import retrofit2.http.*

interface API {
    @GET("test/")
    fun getData(): Call<data>

    @POST("test/")
    fun postData(@Body userData: dataItem): Call<dataItem>

    @PUT("/test/{id}")
    fun updateData(@Path("id") id: Int, @Body userData: dataItem): Call<dataItem>

    @DELETE("/test/{id}")
    fun deleteData(@Path("id") id: Int): Call<Void>
}