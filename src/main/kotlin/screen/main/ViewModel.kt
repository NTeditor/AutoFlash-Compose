package screen.main

import Shell
import ShellResult
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.github.nteditor.autoflash_compose.generated.resources.*
import flashBoot
import flashGSI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString


class ViewModel : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.IO)

    val showRebootMenu = mutableStateOf(false)
    val showFlashMenu = mutableStateOf(false)
    val showDevicesMenu = mutableStateOf(false)
    var logText = mutableStateListOf<String>()
    var enableButton by mutableStateOf(true)

    fun adbDevices() {
        logText.clear()
        scope.launch {
            enableButton = false
            logText.add(getString(Res.string.adb_devices))
            Shell(listOf("adb", "devices")).start().collect {
                when (it) {
                    is ShellResult.Output -> logText.add(it.output)
                    is ShellResult.ExitCode -> {
                        if (it.exitCode == 0) {
                            logText.add("${getString(Res.string.error_code)} 0")
                            logText.add(getString(Res.string.done))
                        } else {
                            logText.add("${getString(Res.string.error_code)} ${it.exitCode}")
                        }
                    }

                    is ShellResult.IsSuccess -> {}
                }
            }
            enableButton = true
        }
    }

    fun fastbootDevices() {
        logText.clear()
        scope.launch {
            enableButton = false
            logText.add(getString(Res.string.fastboot_devices))
            Shell(listOf("fastboot", "device")).start().collect {
                when (it) {
                    is ShellResult.Output -> logText.add(it.output)
                    is ShellResult.ExitCode -> {
                        if (it.exitCode == 0) {
                            logText.add("${getString(Res.string.error_code)} 0")
                            logText.add(getString(Res.string.done))
                        } else {
                            logText.add("${getString(Res.string.error_code)} ${it.exitCode}")
                        }
                    }

                    is ShellResult.IsSuccess -> {}
                }
            }
            enableButton = true
        }
    }

    fun rebootS2(to: String) {
        scope.launch {
            enableButton = false
            logText.add("${getString(Res.string.reboot_to)} $to")
            Shell(listOf("adb", "reboot", to)).start().collect {
                when (it) {
                    is ShellResult.Output -> logText.add(it.output)
                    is ShellResult.ExitCode -> {
                        if (it.exitCode == 0) {
                            logText.add("${getString(Res.string.error_code)} 0")
                            logText.add(getString(Res.string.done))
                        } else {
                            logText.add("${getString(Res.string.error_code)} ${it.exitCode}")
                        }
                    }
                    is ShellResult.IsSuccess -> {}
                }
            }
            enableButton = true
        }
    }

    fun rebootF2(to: String) {
        logText.clear()
        scope.launch {
            enableButton = false
            logText.add("${getString(Res.string.reboot_to)} $to")
            Shell(listOf("fastboot", "reboot", to)).start().collect {
                when (it) {
                    is ShellResult.Output -> logText.add(it.output)
                    is ShellResult.ExitCode -> {
                        if (it.exitCode == 0) {
                            logText.add("${getString(Res.string.error_code)} 0")
                            logText.add(getString(Res.string.done))
                        } else {
                            logText.add("${getString(Res.string.error_code)} ${it.exitCode}")
                        }
                    }

                    is ShellResult.IsSuccess -> {}
                }
            }
            enableButton = true
        }
    }


    fun flashBoot() {
        logText.clear()
        scope.launch {
            enableButton = false
            flashBoot(logText)
            enableButton = true
        }
    }

    fun flashGSI() {
        logText.clear()
        scope.launch {
            enableButton = false
            flashGSI(logText)
            enableButton = true
        }
    }
}