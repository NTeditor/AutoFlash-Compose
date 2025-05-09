package screen.viewmodel

import Shell
import ShellResult
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.dialogs.openFilePicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeViewModel : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.IO)

    val rebootMenuOpen = mutableStateOf(false)
    val flashMenuOpen = mutableStateOf(false)
    val devicesMenuOpen = mutableStateOf(false)
    val logText = mutableStateOf("")

    fun adbDevices() {
        scope.launch {
            logText.value = "Список устройств подключеные по ADB:"
            Shell(listOf("adb", "devices")).start().collect { value ->
                if (value is ShellResult.Output) {
                    logText.value = "${logText.value} \n ${value.output}"
                } else if (value is ShellResult.ExitCode) {
                    logText.value = "${logText.value} \n ExitCode ${value.exitCode}"
                }
            }
        }
    }

    fun fastbootDevices() {
        scope.launch {
            logText.value = "Список устройств подключеные по Fastboot:"
            Shell(listOf("fastboot", "device")).start().collect { value ->
                if (value is ShellResult.Output) {
                    logText.value = "${logText.value} \n ${value.output}"
                } else if (value is ShellResult.ExitCode) {
                    logText.value = "${logText.value} \n ExitCode ${value.exitCode}"
                }
            }
        }
    }

    fun rebootS2(to: String) {
        scope.launch {
            logText.value = "Список устройств подключеные по Fastboot:"
            Shell(listOf("adb", "reboot", to)).start().collect { value ->
                if (value is ShellResult.Output) {
                    logText.value = "${logText.value} \n ${value.output}"
                } else if (value is ShellResult.ExitCode) {
                    logText.value = "${logText.value} \n ExitCode ${value.exitCode}"
                }
            }
        }
    }

    fun rebootF2(to: String) {
        scope.launch {
            logText.value = "Список устройств подключеные по Fastboot:"
            Shell(listOf("fastboot", "reboot", to)).start().collect { value ->
                if (value is ShellResult.Output) {
                    logText.value = "${logText.value} \n ${value.output}"
                } else if (value is ShellResult.ExitCode) {
                    logText.value = "${logText.value} \n ExitCode ${value.exitCode}"
                }
            }
        }
    }


    fun flashBoot() {
        scope.launch {
            logText.value = "Прошивка Boot: \n"


        }
    }

    fun flashGSI() {

    }
}