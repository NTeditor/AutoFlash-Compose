import com.github.nteditor.autoflash_compose.generated.resources.Res
import com.github.nteditor.autoflash_compose.generated.resources.adb_devices
import com.github.nteditor.autoflash_compose.generated.resources.error_code
import com.github.nteditor.autoflash_compose.generated.resources.fastboot_devices
import org.jetbrains.compose.resources.getString
import shell.Shell
import shell.ShellResult

suspend fun getAdbDevices(logText: MutableList<String>) {
    logText.clear()
    logText.add(getString(Res.string.adb_devices))
    Shell().cmd(listOf("adb", "devices")).collect {
        if (it is ShellResult.Output) {
            logText.add(it.output)
        } else if (it is ShellResult.ExitCode && it.exitCode != 0) {
            logText.add("${getString(Res.string.error_code)} ${it.exitCode}")
        }
    }
}

suspend fun getFastbootDevices(logText: MutableList<String>) {
    logText.add(getString(Res.string.fastboot_devices))
    Shell().cmd(listOf("fastboot", "device")).collect {
        if (it is ShellResult.Output) {
            logText.add(it.output)
        } else if (it is ShellResult.ExitCode && it.exitCode != 0) {
            logText.add("${getString(Res.string.error_code)} ${it.exitCode}")
        }
    }
}