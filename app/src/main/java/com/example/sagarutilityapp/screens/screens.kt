package com.example.sagarutilityapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sagarutilityapp.model.WeatherResponse
import com.example.sagarutilityapp.viewmodel.SettingsViewModel
import com.example.sagarutilityapp.viewmodel.WeatherViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = koinViewModel()) {
    val weatherList by viewModel.weatherList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Australia Weather", style = MaterialTheme.typography.headlineMedium)
            IconButton(onClick = { viewModel.refreshWeather() }, enabled = !isLoading) {
                Icon(Icons.Default.Refresh, contentDescription = "Refresh")
            }
        }

        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading && weatherList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(weatherList) { weather ->
                    WeatherItem(weather = weather)
                }
            }
        }
    }
}

@Composable
fun WeatherItem(weather: WeatherResponse) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "${weather.cityName}, ${weather.country}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text("Temp: ${weather.temp}°C", style = MaterialTheme.typography.bodyMedium)
                Text("Humidity: ${weather.humidity}%", style = MaterialTheme.typography.bodySmall)
                Text(
                    "Description: ${weather.description}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = koinViewModel()) {
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val language by viewModel.language.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Settings", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Dark Theme")
            Switch(
                checked = isDarkMode,
                onCheckedChange = { viewModel.toggleDarkMode(it) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Language", style = MaterialTheme.typography.titleMedium)
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = language == "en",
                onClick = { viewModel.setLanguage("en") }
            )
            Text("English")
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = language == "hi",
                onClick = { viewModel.setLanguage("hi") }
            )
            Text("Hindi")
        }
    }
}
