import com.github.nteditor.autoflash_compose.generated.resources.Res
import com.github.nteditor.autoflash_compose.generated.resources.done
import com.github.nteditor.autoflash_compose.generated.resources.error_code
import com.github.nteditor.autoflash_compose.generated.resources.reboot_to
import org.jetbrains.compose.resources.getString
import shell.Shell
import shell.ShellResult

suspend fun rebootF2(to: String, logText: MutableList<String>) {
    logText.clear()
    logText.add("${getString(Res.string.reboot_to)} $to")
    Shell.cmd(listOf("fastboot", "reboot", to)).collect {
        if (it is ShellResult.Output) {
            logText.add(it.output)
        } else if (it is ShellResult.ExitCode && it.exitCode != 0) {
            logText.add("${getString(Res.string.error_code)} ${it.exitCode}")
        } else if (it is ShellResult.ExitCode) {
            logText.add(getString(Res.string.done))
        }
    }
}

suspend fun rebootS2(to: String, logText: MutableList<String>) {
    logText.clear()
    logText.add("${getString(Res.string.reboot_to)} $to")
    Shell.cmd(listOf("adb", "reboot", to)).collect {
        if (it is ShellResult.Output) {
            logText.add(it.output)
        } else if (it is ShellResult.ExitCode && it.exitCode != 0) {
            logText.add("${getString(Res.string.error_code)} ${it.exitCode}")
        } else if (it is ShellResult.ExitCode) {
            logText.add(getString(Res.string.done))
        }
    }
}