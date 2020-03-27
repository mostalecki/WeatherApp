package com.example.weatherapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.repository.WeatherRepository

class WeatherViewModel (application: Application) : AndroidViewModel(application) {
    private var repository: WeatherRepository = WeatherRepository()


    suspend fun getWeatherForCity(city: String) : Boolean{
        try {
            repository.getCurrentWeather(city)
        } catch (e: Exception){
            return false
        }
        return true
    }

    fun currentWeather(): WeatherResponse{
        return repository.currentWeather
    }
}