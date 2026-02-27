package com.example.sagarutilityapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sagarutilityapp.model.WeatherUi
import com.example.sagarutilityapp.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val repo = WeatherRepository()

    // ✅ Default city = Brisbane
    private val _query = MutableStateFlow("Brisbane")
    val query: StateFlow<String> = _query

    private val _weather = MutableStateFlow<WeatherUi?>(null)
    val weather: StateFlow<WeatherUi?> = _weather

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // Automatically load Brisbane weather when app starts
    init {
        search()
    }

    fun setQuery(text: String) {
        _query.value = text
    }

    fun search() {
        val city = _query.value.trim()
        if (city.isEmpty()) return

        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                _weather.value = repo.getWeatherForCity(city)
            } catch (e: Exception) {
                _error.value = e.message ?: "Something went wrong"
                _weather.value = null
            } finally {
                _loading.value = false
            }
        }
    }
}