package screen.main

import Shell
import ShellResult
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ViewModel : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.IO)

    val showRebootMenu = mutableStateOf(false)
    val showFlashMenu = mutableStateOf(false)
    val showDevicesMenu = mutableStateOf(false)
    var logText = mutableStateListOf<String>()

    fun adbDevices() {
        scope.launch {
            logText.add("Список устройств подключеные по ADB:")
            Shell(listOf("adb", "devices")).start().collect { value ->
                if (value is ShellResult.Output) {
                    logText.add(value.output)
                } else if (value is ShellResult.ExitCode) {
                    logText.add("ExitCode: ${value.exitCode}")
                }
            }
        }
    }

    fun fastbootDevices() {
        scope.launch {
            logText.add("Список устройств подключеные по Fastboot:")
            Shell(listOf("fastboot", "device")).start().collect { value ->
                if (value is ShellResult.Output) {
                    logText.add(value.output)
                } else if (value is ShellResult.ExitCode) {
                    logText.add("ExitCode: ${value.exitCode}")
                }
            }
        }
    }

    fun rebootS2(to: String) {
        scope.launch {
            logText.add("Список устройств подключеные по Fastboot:")
            Shell(listOf("adb", "reboot", to)).start().collect { value ->
                if (value is ShellResult.Output) {
                    logText.add(value.output)
                } else if (value is ShellResult.ExitCode) {
                    logText.add("ExitCode: ${value.exitCode}")
                }
            }
        }
    }

    fun rebootF2(to: String) {
        scope.launch {
            logText.add("Список устройств подключеные по Fastboot:")
            Shell(listOf("fastboot", "reboot", to)).start().collect { value ->
                if (value is ShellResult.Output) {
                    logText.add(value.output)
                } else if (value is ShellResult.ExitCode) {
                    logText.add("ExitCode: ${value.exitCode}")
                }
            }
        }
    }


    fun flashBoot() {
        scope.launch {
            logText.add("Прошивка Boot: \n")

        }
    }

    fun flashGSI() {

    }
}