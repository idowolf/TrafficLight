package com.example.trafficlight

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import com.example.trafficlight.Command.Companion.convertToUdp
import com.example.trafficlight.ui.theme.TrafficlightTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress


private const val ipAddress = "192.168.86.162"
private const val port = "1234"

const val TIMEOUT = 2000L
const val MAX_PRESSES = 5

class MainActivity : ComponentActivity() {

    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferencesManager = PreferencesManager(this)
        setContent {
            TrafficlightTheme {
                val pressTimes = remember { mutableStateListOf<Long>() }
                var parentScreen by remember { mutableStateOf(false) }
                val haptic = LocalHapticFeedback.current

                fun parentButtonClicked() {
                    val currentTime = System.currentTimeMillis()
                    pressTimes.add(currentTime)

                    if (pressTimes.size > MAX_PRESSES) {
                        pressTimes.removeAt(0)
                    }

                    if (pressTimes.size == MAX_PRESSES &&
                        (currentTime - pressTimes.first()) < TIMEOUT
                    ) {
                        pressTimes.clear()
                        parentScreen = true
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    if (parentScreen) {
                        ParentScreen(ipAddress, port) {
                            parentScreen = false
                        }
                    } else {
                        TrafficLightScreen(Modifier, {
                            if (preferencesManager.getBoolean(enableHapticFeedbackKey, true)) {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            }
                            sendUDP(it)
                        }) {
                            parentButtonClicked()
                        }
                    }
                }

            }
        }
    }

    private fun sendUDP(command: Command) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val socket = DatagramSocket()
                val address = InetAddress.getByName(preferencesManager.getString(ipAddressKey, ipAddress))
                val udp = command.convertToUdp()
                Log.d(MainActivity::class.java.simpleName, udp)
                val buf = udp.toByteArray()
                val packet = DatagramPacket(buf, buf.size, address, preferencesManager.getString(
                    portKey, port).toInt())
                socket.send(packet)
                socket.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}



