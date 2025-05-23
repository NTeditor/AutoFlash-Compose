import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.github.nteditor.autoflash_compose.generated.resources.Res
import com.github.nteditor.autoflash_compose.generated.resources.app_name
import io.github.vinceglb.filekit.FileKit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import screen.main.HomeScreen
import shell.Shell
import ui.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        HomeScreen()
    }
}


fun main() = application {
    System.setProperty("skiko.renderApi", "OPENGL")
    FileKit.init(appId = stringResource(Res.string.app_name))

    Window(
        onCloseRequest = {
            CoroutineScope(Dispatchers.IO).launch {
                Shell.stop()
                if (Shell.Shell.isAdbInstall()) {
                    Shell.Shell.cmd(listOf("adb", "kill-server")).collect {}
                }
                exitApplication()
            }
        },
        title = stringResource(Res.string.app_name)
    ) {
        App()
    }
}
