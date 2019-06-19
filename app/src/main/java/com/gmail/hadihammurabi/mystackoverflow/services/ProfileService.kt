package com.gmail.hadihammurabi.mystackoverflow.services

import com.gmail.hadihammurabi.mystackoverflow.models.Profile
import com.gmail.hadihammurabi.mystackoverflow.models.ProfileResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ProfileService {
    @GET("users/11132361?site=stackoverflow")
    fun getProfile(): Observable<ProfileResponse>

    companion object {
        fun create(): ProfileService {
            val client = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.stackexchange.com/2.2/")
                .build()
            return client.create(ProfileService::class.java)
        }
    }

}
