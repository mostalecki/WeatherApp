package com.example.weatherapp.api

import com.example.weatherapp.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

const val APIkey = "03649a9d8c519d676a3a4451ac0d9d65"

interface Webservice {

    @GET("weather?appid=${APIkey}&lang=pl&units=metric")
    suspend fun getWeatherForCity(@Query("q") city: String) : WeatherResponse
}
