package screen.main

import Shell
import ShellResult
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
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

    fun adbDevices() {
        scope.launch {
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
        }
    }

    fun fastbootDevices() {
        scope.launch {
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
        }
    }

    fun rebootS2(to: String) {
        scope.launch {
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
        }
    }

    fun rebootF2(to: String) {
        logText.clear()
        scope.launch {
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
        }
    }


    fun flashBoot() {
        logText.clear()
        scope.launch {
            flashBoot(logText)
        }
    }

    fun flashGSI() {
        logText.clear()
        scope.launch {
            flashGSI(logText)
        }
    }
}