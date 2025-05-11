package screen.main

import Shell
import ShellResult
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.openFilePicker
import io.github.vinceglb.filekit.path
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
        logText.clear()
        logText.add("Прошивка Boot:")
        scope.launch {
            val file = FileKit.openFilePicker(type = FileKitType.File(listOf("img", "bin")))
            if (file != null) {
                Shell(listOf("fastboot", "flash", "boot", file.path)).start().collect {
                    when (it) {
                        is ShellResult.Output -> logText.add(it.output)
                        is ShellResult.ExitCode -> logText.add("ExitCode: ${it.exitCode}")
                        is ShellResult.IsSuccess -> {}
                    }
                }
                logText.add("Готово!")
            } else {
                logText.add("Отмена..")
            }
        }
    }

    fun flashGSI() {
        logText.clear()
        logText.add("Прошивка GSI:")
    }
}