import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.vinceglb.filekit.FileKit
import screen.HomeScreen
import ui.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        HomeScreen()
    }
}


fun main() = application {
    System.setProperty("skiko.renderApi", "OPENGL")
    FileKit.init(appId = "AutoFlash")

    Window(
        onCloseRequest = {
            Shell.stop()
            exitApplication()
        },
        title = "AutoFlash"
    ) {
        App()
    }
}
