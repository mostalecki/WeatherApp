package com.example.weatherapp.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide

import com.example.weatherapp.R
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.viewmodels.WeatherViewModel
import kotlinx.android.synthetic.main.fragment_city_detail.view.*
import java.text.SimpleDateFormat
import java.util.*

class CityDetailFragment : Fragment() {

    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var currentWeather: WeatherResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        weatherViewModel = ViewModelProviders.of(activity!!).get(WeatherViewModel::class.java)
        currentWeather = weatherViewModel.currentWeather()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_city_detail, container, false)
        // Set icons for flag and weather
        Glide.with(activity!!).load("https://www.countryflags.io/${currentWeather.sys.country}/shiny/64.png").into(view.countryIcon)
        Glide.with(activity!!).load("https://openweathermap.org/img/wn/${currentWeather.weather[0].icon}@2x.png").into(view.weatherIcon)

        // Set city name, current date, temperature and wind
        view.cityName.text = currentWeather.name
        var sdf = SimpleDateFormat("HH:mm, dd.mm.yyyy")
        val currentDate = Date()
        val dateOffset = Date().timezoneOffset * 60
        currentDate.time += (currentWeather.timezone + dateOffset) * 1000
        view.datetime.text = sdf.format(currentDate)
        view.temperature.text = "%.1f°C".format(currentWeather.main.temp)
        view.wind.text = "%.1f km/h".format(currentWeather.wind.speed * 36 / 10) // convert m/s to km/h

        // Set description
        view.description.text = currentWeather.weather[0].description.capitalize()

        //Set sunrise, sunset, air pressure, humidity
        sdf = SimpleDateFormat("HH:mm:ss")
        view.sunrise.text = sdf.format(Date((currentWeather.sys.sunrise + currentWeather.timezone + dateOffset) * 1000))
        view.sunset.text = sdf.format(Date((currentWeather.sys.sunset + currentWeather.timezone + dateOffset) * 1000))
        view.pressure.text = "${currentWeather.main.pressure} hPa"
        view.humidity.text = "${currentWeather.main.humidity} %"

        //Set background gradient
        //lateinit var gd: GradientDrawable //GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(Color.BLUE, Color.RED))
        var colors = IntArray(2)
        when (currentWeather.weather[0].id) {
            in 200..299 -> {
                // thunderstorm
                colors[0] = Color.parseColor("#1a2025")
                colors[1] = Color.parseColor("#575757")
            }
            in 300..399 -> {
                // drizzle
                colors[0] = Color.parseColor("#535454")
                colors[1] = Color.parseColor("#bbb7b7")
            }
            in 500..599 -> {
                // rain
                colors[0] = Color.parseColor("#3e7080")
                colors[1] = Color.parseColor("#fcf9f9")
            }
            in 600..699 -> {
                // snow
                colors[0] = Color.parseColor("#bbb7b7")
                colors[1] = Color.parseColor("#cbcdcd")
            }
            800 -> {
                // clear
                colors[0] = Color.parseColor("#047dd1")
                colors[1] = Color.parseColor("#12c6e7")
            }
            in 801..899 -> {
                // clouds
                colors[0] = Color.parseColor("#636f73")
                colors[1] = Color.parseColor("#fcf9f9")
            }
        }
        var gd = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors)
        view.background = gd


        return view
    }

}
