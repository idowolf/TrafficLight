package com.example.trafficlight

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedPrefsTextField(prefKey: String, defaultValue: String, label: String) {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    val data = remember { mutableStateOf(preferencesManager.getString(prefKey, defaultValue)) }

    TextField(
        value = data.value,
        onValueChange = {
            preferencesManager.setString(prefKey, it)
            data.value = it
        },
        label = { Text(label) }
    )
}
