import com.github.nteditor.autoflash_compose.generated.resources.*
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.openFilePicker
import io.github.vinceglb.filekit.path
import org.jetbrains.compose.resources.getString

suspend fun flashGSI(logText: MutableList<String>) {
    var exit = false
    logText.clear()

    logText.add(getString(Res.string.flashing_gsi))
    val file = FileKit.openFilePicker(type = FileKitType.File(listOf("img", "bin")))
    if (file == null) {
        logText.add(getString(Res.string.cancel))
        return
    }

    Shell().cmd(listOf("fastboot", "erase", "system")).collect {
        if (it is ShellResult.Output) {
            logText.add(it.output)
        } else if (it is ShellResult.ExitCode && it.exitCode != 0) {
            logText.add("${getString(Res.string.error_code)} ${it.exitCode}")
            exit = true
        }
    }

    if (exit) return
    Shell().cmd(listOf("fastboot", "delete-logical-partition", "product_a")).collect {
        if (it is ShellResult.Output) {
            logText.add(it.output)
        } else if (it is ShellResult.ExitCode && it.exitCode != 0) {
            logText.add("${getString(Res.string.error_code)} ${it.exitCode}")
            exit = true
        }
    }

    if (exit) return
    Shell().cmd(listOf("fastboot", "delete-logical-partition", "product_b")).collect {
        if (it is ShellResult.Output) {
            logText.add(it.output)
        } else if (it is ShellResult.ExitCode && it.exitCode != 0) {
            logText.add("${getString(Res.string.error_code)} ${it.exitCode}")
            exit = true
        }
    }

    if (exit) return
    Shell().cmd(listOf("fastboot", "flash", "system", file.path)).collect {
        if (it is ShellResult.Output) {
            logText.add(it.output)
        } else if (it is ShellResult.ExitCode && it.exitCode != 0) {
            logText.add("${getString(Res.string.error_code)} ${it.exitCode}")
            exit = true
        } else if (it is ShellResult.ExitCode) {
            logText.add(getString(Res.string.done))
        }
    }
}

suspend fun flashBoot(logText: MutableList<String>) {
    logText.clear()
    logText.add(getString(Res.string.flashing_boot))
    val file = FileKit.openFilePicker(type = FileKitType.File(listOf("img", "bin")))
    if (file == null) {
        logText.add(getString(Res.string.cancel))
        return
    }
    Shell().cmd(listOf("fastboot", "flash", "boot", file.path)).collect {
        if (it is ShellResult.Output) {
            logText.add(it.output)
        } else if (it is ShellResult.ExitCode && it.exitCode != 0) {
            logText.add("${getString(Res.string.error_code)} ${it.exitCode}")
        }
    }
}