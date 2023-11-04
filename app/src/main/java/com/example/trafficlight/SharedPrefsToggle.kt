package com.example.trafficlight

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Suppress("SameParameterValue")
@Composable
fun SharedPrefsToggle(prefKey: String, label: String, defaultValue: Boolean) {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    val data = remember { mutableStateOf(preferencesManager.getBoolean(prefKey, defaultValue)) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Switch(checked = data.value, onCheckedChange = {
            preferencesManager.setBoolean(prefKey, it)
            data.value = it
        })
        Spacer(modifier = Modifier.size(5.dp))
        Text(text = label)
    }
}

@Preview
@Composable
fun SharedPrefsTogglePreview() {
    SharedPrefsToggle(prefKey = "", "Bla bla", defaultValue = false)
}