package com.example.sagarutilityapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.sagarutilityapp.screens.SettingsScreen
import com.example.sagarutilityapp.screens.WeatherScreen
import com.example.sagarutilityapp.ui.theme.SagarUtilityAppTheme
import com.example.sagarutilityapp.viewmodel.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsViewModel: SettingsViewModel = koinViewModel()
            val isDarkMode by settingsViewModel.isDarkMode.collectAsState()

            SagarUtilityAppTheme(darkTheme = isDarkMode) {
                SagarUtilityApp()
            }
        }
    }
}

@Composable
fun SagarUtilityApp() {
    var selectedTab by remember { mutableStateOf("Weather") }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Weather") },
                    label = { Text("Weather") },
                    selected = selectedTab == "Weather",
                    onClick = { selectedTab = "Weather" }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") },
                    selected = selectedTab == "Settings",
                    onClick = { selectedTab = "Settings" }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                "Weather" -> WeatherScreen()
                "Settings" -> SettingsScreen()
            }
        }
    }
}
