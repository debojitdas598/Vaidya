package com.example.vaidya.api

import com.example.vaidya.datamodel.DataModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers


interface ApiModule {

    @Headers("X-Auth-Token:BBUS-ZdDrvmbwHMtcEsf1SUyDv5BcpU2B2y")
    @GET("/65c7a2f0c7362f000d1485c6/values")
    fun getData(): Call<DataModel?>?

}