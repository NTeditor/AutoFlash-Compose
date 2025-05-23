package screen.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import flashBoot
import flashGSI
import getAdbDevices
import getFastbootDevices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import rebootF2
import rebootS2
import shell.Shell


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
            enableButton = false
            getAdbDevices(logText)
            enableButton = true
        }
    }

    fun fastbootDevices() {
        scope.launch {
            enableButton = false
            getFastbootDevices(logText)
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