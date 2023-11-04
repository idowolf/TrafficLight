package com.example.trafficlight

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.trafficlight.ui.theme.TrafficlightTheme

@Composable
fun ParentScreen(defaultIp: String, defaultPort: String, onCloseClicked: () -> Unit) {
    Column(modifier = Modifier.height(50.dp)) {
        Button(onClick = { onCloseClicked() }, modifier = Modifier.padding(5.dp)) {
            Text(text = "X")
        }
    }
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        SharedPrefsTextField(prefKey = ipAddressKey, defaultValue = defaultIp, label = "IP Address")
        SharedPrefsTextField(prefKey = portKey, defaultValue = defaultPort, label = "Port")
        SharedPrefsToggle(prefKey = useYellowKey, "Use Yellow", defaultValue = false)
        SharedPrefsToggle(prefKey = enableHapticFeedbackKey, "Enable Haptic Feedback", defaultValue = true)
    }
}

@Preview(showBackground = true)
@Composable
fun ParentScreenPreview() {
    TrafficlightTheme {
        ParentScreen("", "", {})
    }
}