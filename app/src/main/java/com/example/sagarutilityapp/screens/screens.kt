package com.example.sagarutilityapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sagarutilityapp.model.QuoteViewModel

@Composable
fun UtilityScreen() {
//    var counter by remember { mutableIntStateOf(0) }
//    val vm: CounterViewModel = viewModel()
//    val counter by vm.count.collectAsState()
    val vm: QuoteViewModel = viewModel()
    val quote by vm.quote.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Utility Screen", style = MaterialTheme.typography.headlineMedium)
//        Text("Counter: $counter", style = MaterialTheme.typography.bodyLarge)

        Button(onClick = { vm.loadQuote() }) {
//        Button(onClick = { vm.increment() }) {
            Text(text = "Get quote")
        }
        Text(quote, style = MaterialTheme. typography.bodyLarge)
    }
}
@Composable
fun SettingsScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp), Arrangement.spacedBy(16.dp)
    ) {
        Text("Settings Screen", style = MaterialTheme.typography.headlineMedium)
        Text("This is where you can add toggles or preferences.")
    }
}