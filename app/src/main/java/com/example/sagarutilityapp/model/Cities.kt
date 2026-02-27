package com.example.sagarutilityapp.model

data class City(val name: String, val country: String, val lat: Double, val lon: Double) {
    val label: String get() = "$name, $country"
}

val TOP_CITIES: List<City> = listOf(
    City("Sydney","AU",-33.8688,151.2093),
    City("Melbourne","AU",-37.8136,144.9631),
    City("Brisbane","AU",-27.4698,153.0251),
    City("Perth","AU",-31.9505,115.8605),
    City("Adelaide","AU",-34.9285,138.6007),
    City("Canberra","AU",-35.2809,149.1300),

    City("New York","US",40.7128,-74.0060),
    City("London","GB",51.5074,-0.1278),
    City("Tokyo","JP",35.6762,139.6503),
    City("Dubai","AE",25.2048,55.2708),
    City("Singapore","SG",1.3521,103.8198)
)