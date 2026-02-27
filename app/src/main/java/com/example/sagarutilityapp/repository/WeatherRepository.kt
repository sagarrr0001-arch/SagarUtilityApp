package com.example.sagarutilityapp.repository

import com.example.sagarutilityapp.model.WeatherUi
import com.example.sagarutilityapp.model.toWeatherDescription
import com.example.sagarutilityapp.network.RetrofitClient

class WeatherRepository {

    suspend fun getWeatherForCity(cityName: String): WeatherUi {
        val geo = RetrofitClient.geoApi.searchCity(cityName)
        val place = geo.results?.firstOrNull()
            ?: throw Exception("City not found")

        val weather = RetrofitClient.weatherApi.getCurrentWeather(
            lat = place.latitude,
            lon = place.longitude
        )

        return WeatherUi(
            city = "${place.name}${place.country?.let { ", $it" } ?: ""}",
            tempC = weather.current.temperature_2m,
            description = weather.current.weather_code.toWeatherDescription()
        )
    }
}