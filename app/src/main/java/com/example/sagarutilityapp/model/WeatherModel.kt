package com.example.sagarutilityapp.model

data class WeatherApiResponse(
    val current: CurrentWeather
)

data class CurrentWeather(
    val temperature_2m: Double,
    val weather_code: Int
)

data class WeatherUi(
    val city: String,
    val tempC: Double,
    val description: String
)

fun Int.toWeatherDescription(): String = when (this) {
    0 -> "Clear sky"
    1, 2, 3 -> "Partly cloudy"
    45, 48 -> "Fog"
    51, 53, 55 -> "Drizzle"
    61, 63, 65 -> "Rain"
    71, 73, 75 -> "Snow"
    80, 81, 82 -> "Showers"
    95 -> "Thunderstorm"
    96, 99 -> "Thunderstorm (hail)"
    else -> "Unknown"
}