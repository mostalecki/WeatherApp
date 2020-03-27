package com.example.weatherapp.repository

import android.util.Log
import com.example.weatherapp.api.RetrofitClient
import com.example.weatherapp.api.Webservice
import com.example.weatherapp.model.WeatherResponse
import retrofit2.HttpException
import java.lang.Exception

class WeatherRepository() {
    private var client: Webservice = RetrofitClient.webservice
    lateinit var currentWeather : WeatherResponse


    suspend fun getCurrentWeather(city: String): Boolean{
        try {
            currentWeather = client.getWeatherForCity(city)
        } catch (e: HttpException){
            if (e.code() == 404) {
                throw Exception("xdd")
            }
        }
        return true
    }
}