package screen.main

import Shell
import ShellResult
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.github.nteditor.autoflash_compose.generated.resources.Res
import com.github.nteditor.autoflash_compose.generated.resources.adb_devices
import com.github.nteditor.autoflash_compose.generated.resources.error_code
import com.github.nteditor.autoflash_compose.generated.resources.fastboot_devices
import flashBoot
import flashGSI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import rebootF2
import rebootS2


class ViewModel : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.IO)

    var showRebootMenu by mutableStateOf(false)
        private set
    var showFlashMenu by mutableStateOf(false)
        private set
    var showDevicesMenu by mutableStateOf(false)
        private set
    var logText = mutableStateListOf<String>()
    var enableButton by mutableStateOf(true)
        private set
    val isAdbInstall = Shell().checkADB()

    fun updateUI(
        showRebootMenu: Boolean? = null,
        showFlashMenu: Boolean? = null,
        showDevicesMenu: Boolean? = null,
    ) {
        if (showRebootMenu != null) this.showRebootMenu = showRebootMenu
        if (showFlashMenu != null) this.showFlashMenu = showFlashMenu
        if (showDevicesMenu != null) this.showDevicesMenu = showDevicesMenu
    }

    fun adbDevices() {
        scope.launch {
            logText.clear()
            enableButton = false
            logText.add(getString(Res.string.adb_devices))
            Shell().cmd(listOf("adb", "devices")).collect {
                if (it is ShellResult.Output) {
                    logText.add(it.output)
                } else if (it is ShellResult.ExitCode && it.exitCode != 0) {
                    logText.add("${getString(Res.string.error_code)} ${it.exitCode}")
                }
            }
            enableButton = true
        }
    }

    fun fastbootDevices() {
        scope.launch {
            logText.clear()
            enableButton = false
            logText.add(getString(Res.string.fastboot_devices))
            Shell().cmd(listOf("fastboot", "device")).collect {
                if (it is ShellResult.Output) {
                    logText.add(it.output)
                } else if (it is ShellResult.ExitCode && it.exitCode != 0) {
                    logText.add("${getString(Res.string.error_code)} ${it.exitCode}")
                }
            }
            enableButton = true
        }
    }

    fun rebootS2(to: String) {
        scope.launch {
            enableButton = false
            rebootS2(to, logText)
            enableButton = true
        }
    }

    fun rebootF2(to: String) {
        scope.launch {
            enableButton = false
            rebootF2(to, logText)
            enableButton = true
        }
    }


    fun flashBoot() {
        scope.launch {
            enableButton = false
            flashBoot(logText)
            enableButton = true
        }
    }

    fun flashGSI() {
        scope.launch {
            enableButton = false
            flashGSI(logText)
            enableButton = true
        }
    }
}