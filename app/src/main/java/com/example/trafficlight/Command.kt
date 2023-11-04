package com.example.trafficlight

sealed class Command(val commandValue: Int) {

    class Update(vararg val ledCommands: Pair<LedPosition, Colors>) : Command(0)

    class UpdateAndShow(vararg val ledCommands: Pair<LedPosition, Colors>) : Command(1)

    data class BrightnessControl(val brightness: UByte) : Command(2)

    object Show : Command(3)

    object Off : Command(255)

    companion object {
        fun Command.convertToUdp(): String {
            Update(Pair(LedPosition.Red, Colors.Red))
            return when (this) {
                is BrightnessControl -> "$commandValue,$brightness"
                is Update -> "$commandValue,${
                    ledCommands.joinToString(",") { (led, color) -> "${led.ordinal},${color.red},${color.green},${color.blue}" }
                }"

                is UpdateAndShow -> "$commandValue,${
                    ledCommands.joinToString(",") { (led, color) -> "${led.ordinal},${color.red},${color.green},${color.blue}" }
                }"

                Off -> "$commandValue"
                Show -> "$commandValue"
            }
        }
    }
}