import com.github.nteditor.autoflash_compose.generated.resources.Res
import com.github.nteditor.autoflash_compose.generated.resources.cancel
import com.github.nteditor.autoflash_compose.generated.resources.done
import com.github.nteditor.autoflash_compose.generated.resources.error_code
import com.github.nteditor.autoflash_compose.generated.resources.flashing_boot
import com.github.nteditor.autoflash_compose.generated.resources.flashing_gsi
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.openFilePicker
import io.github.vinceglb.filekit.path
import org.jetbrains.compose.resources.getString

suspend fun flashGSI(logText: MutableList<String>) {
    logText.add(getString(Res.string.flashing_gsi))
    val file = FileKit.openFilePicker(type = FileKitType.File(listOf("img", "bin")))
    if (file != null) {
        Shell(listOf("fastboot", "erase", "system")).start().collect {
            if (it is ShellResult.Output) {
                logText.add(it.output)
            }
        }
        Shell(listOf("fastboot", "delete-logical-partition", "product_a")).start().collect {
            if (it is ShellResult.Output) {
                logText.add(it.output)
            }
        }
        Shell(listOf("fastboot", "delete-logical-partition", "product_b")).start().collect {
            if (it is ShellResult.Output) {
                logText.add(it.output)
            }
        }

        Shell(listOf("fastboot", "flash", "system", file.path)).start().collect {
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

    } else {
        logText.add(getString(Res.string.cancel))
    }
}

suspend fun flashBoot(logText: MutableList<String>) {
    logText.add(getString(Res.string.flashing_boot))
    val file = FileKit.openFilePicker(type = FileKitType.File(listOf("img", "bin")))
    if (file != null) {
        Shell(listOf("fastboot", "flash", "boot", file.path)).start().collect {
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
    } else {
        logText.add(getString(Res.string.cancel))
    }
}