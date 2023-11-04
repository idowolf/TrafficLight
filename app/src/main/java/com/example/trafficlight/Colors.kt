package com.example.trafficlight

enum class Colors(val red: Int, val green: Int, val blue: Int) {
    Red(255, 0, 0),
    Green(0, 255, 0),
    Yellow(255, 255, 0),
    Orange(200, 25, 0),
    Off(0, 0, 0)
}