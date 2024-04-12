package com.example.bookwise.Retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHepler {

    companion object{
        val BASE_URL = "http://192.168.0.110:8080"

        @Volatile
        var INSTANCE : Retrofit? = null

        fun getInstance():Retrofit{
            if(INSTANCE == null){
                synchronized(this){
                    INSTANCE = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}