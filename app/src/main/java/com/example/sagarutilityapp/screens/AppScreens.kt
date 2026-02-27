package com.example.sagarutilityapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.sagarutilityapp.viewmodel.SettingsViewModel
import com.example.sagarutilityapp.viewmodel.WeatherViewModel

@Composable
fun AppRoot(settingsVm: SettingsViewModel = viewModel()) {
    val dark by settingsVm.darkTheme.collectAsState()

    MaterialTheme(colorScheme = if (dark) darkColorScheme() else lightColorScheme()) {
        val navController = rememberNavController()

        Scaffold(bottomBar = { BottomBar(navController) }) { padding ->
            NavHost(
                navController = navController,
                startDestination = "weather",
                modifier = Modifier.padding(padding)
            ) {
                composable("weather") { WeatherScreen() }
                composable("settings") { SettingsScreen(settingsVm) }
            }
        }
    }
}

@Composable
private fun BottomBar(navController: NavHostController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == "weather",
            onClick = { navController.navigate("weather") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Weather") },
            label = { Text("Weather") }
        )
        NavigationBarItem(
            selected = currentRoute == "settings",
            onClick = { navController.navigate("settings") },
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            label = { Text("Settings") }
        )
    }
}

@Composable
fun WeatherScreen(vm: WeatherViewModel = viewModel()) {
    val query by vm.query.collectAsState()
    val weather by vm.weather.collectAsState()
    val loading by vm.loading.collectAsState()
    val error by vm.error.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Weather", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = query,
            onValueChange = vm::setQuery,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Search city") },
            singleLine = true
        )

        Button(
            onClick = { vm.search() },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Search") }

        if (loading) CircularProgressIndicator()
        error?.let { Text("Error: $it") }

        weather?.let {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(it.city, style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(8.dp))
                    Text("Temp: ${String.format("%.1f", it.tempC)}°C")
                    Text("Condition: ${it.description}")
                }
            }
        }
    }
}

@Composable
fun SettingsScreen(settingsVm: SettingsViewModel) {
    val dark by settingsVm.darkTheme.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Settings", style = MaterialTheme.typography.headlineSmall)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Dark Theme")
            Switch(
                checked = dark,
                onCheckedChange = { settingsVm.setDarkTheme(it) }
            )
        }
    }
}