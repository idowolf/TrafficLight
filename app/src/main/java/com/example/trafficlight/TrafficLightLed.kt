package com.example.trafficlight

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TrafficLightLed(
    displayColor: Color,
    onCommandPressed: (Command) -> Unit,
    vararg commands: Pair<LedPosition, Colors>
) {
    Button(onClick = { onCommandPressed(Command.UpdateAndShow(*commands)) },
        modifier = Modifier
            .clip(CircleShape)
            .size(50.dp)
        ,colors = ButtonDefaults.buttonColors(displayColor)
    ) {
    }
}

@Composable
@Preview
fun TrafficLightLedPreview() {
    TrafficLightLed(displayColor = Color.Red, onCommandPressed = {})
}