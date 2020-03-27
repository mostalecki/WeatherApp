package com.example.weatherapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.weatherapp.R
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.viewmodels.WeatherViewModel
import kotlinx.android.synthetic.main.fragment_city_search.*
import kotlinx.android.synthetic.main.fragment_city_search.view.*
import kotlinx.android.synthetic.main.fragment_city_search.view.cityNameInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CitySearchFragment : Fragment() {

    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        weatherViewModel = ViewModelProviders.of(activity!!).get(WeatherViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_city_search, container, false)

        view.checkWeatherButton.setOnClickListener {
            GlobalScope.launch {
                val result = weatherViewModel.getWeatherForCity(view.cityNameInput.text.toString())
                if (result){
                    withContext(Dispatchers.Main){
                        activity!!.supportFragmentManager.beginTransaction().replace(R.id.content, CityDetailFragment()).addToBackStack(null).commit()
                    }
                } else{
                    withContext(Dispatchers.Main){
                        view.citySearchError.text = "Nie znaleziono pogody dla tego miasta."
                    }
                }
            }
        }

        return view
    }
}
