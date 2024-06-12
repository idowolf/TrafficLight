package com.example.trafficlight

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.trafficlight.ui.theme.TrafficlightTheme


@Composable
fun TrafficLightScreen(modifier: Modifier = Modifier, onCommandPressed: (Command) -> Unit, parentButtonClicked: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF35D6ED),
                        Color(0xFF65DDEF),
                        Color(0xFF7AE5F5),
                        Color(0xFF97EBF4),
                        Color(0xFFC9F6FF)
                    )
                )
            )
    ) {
        Row(modifier = Modifier.height(50.dp).fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Button(onClick = { parentButtonClicked() }, modifier = Modifier.size(50.dp).alpha(0f)) {}
        }

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TrafficLight(onCommandPressed)
        }

        Button(onClick = { onCommandPressed(Command.Off) }, modifier = Modifier.padding(5.dp)) {
            Text(text = "Turn Off")
        }
    }
}

@Composable
private fun TrafficLight(onCommandPressed: (Command) -> Unit) {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }

    Column(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(20.dp))
            .background(Color.Black)
            .padding(20.dp),
    ) {
        TrafficLightLed(
            Color.Red,
            onCommandPressed,
            Pair(LedPosition.Top, Colors.Red),
            Pair(LedPosition.Middle, Colors.Off),
            Pair(LedPosition.Bottom, Colors.Off)
        )

        Spacer(modifier = Modifier.size(50.dp))

        TrafficLightLed(
            Color.Yellow,
            onCommandPressed,
            Pair(LedPosition.Middle, if (preferencesManager.getBoolean(useYellowKey, false)) Colors.Yellow else Colors.Orang e),
            Pair(LedPosition.Bottom, Colors.Off)
        )

        Spacer(modifier = Modifier.size(50.dp))

        TrafficLightLed(
            Color.Green,
            onCommandPressed,
            Pair(LedPosition.Top, Colors.Off),
            Pair(LedPosition.Middle, Colors.Off),
            Pair(LedPosition.Bottom, Colors.Green)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun TrafficLightScreenPreview() {
    TrafficlightTheme {
        TrafficLightScreen(Modifier, {}, {})
    }
}