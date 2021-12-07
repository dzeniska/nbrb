package com.dzenis_ska.nbrb.remote_model


import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit
import com.google.gson.GsonBuilder

import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.converter.moshi.MoshiConverterFactory



val BASE_URL_API = "http://www.nbrb.by/Services/"


interface ApiCourse {

    @GET ("XmlExRates.aspx")
    suspend fun getCourse(
    @Query("ondate")
    date: String
): String


    companion object Factory {
        fun create(): ApiCourse {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(ApiCourse::class.java)
        }
    }
}