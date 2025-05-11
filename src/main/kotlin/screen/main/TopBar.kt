package screen.main

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.github.nteditor.autoflash_compose.generated.resources.Res
import com.github.nteditor.autoflash_compose.generated.resources.app_name
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(action: @Composable () -> Unit) {
    TopAppBar(
        title = { Text(stringResource(Res.string.app_name)) },
        actions = {
            action.invoke()
        }
    )
}