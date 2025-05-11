package screen.main

import Shell
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BuildCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ui.theme.ConsoleStyle
import com.github.nteditor.autoflash_compose.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeScreen() {
    val viewModel = viewModel<ViewModel>()
    HomeView(
        viewModel = viewModel
    )
}

@Composable
fun HomeView(
    viewModel: ViewModel
) {
    Scaffold(
        topBar = {
            TopBar {
                RebootMenu(viewModel.showRebootMenu, viewModel)
                FlashMenu(viewModel.showFlashMenu, viewModel)
                DeviceMenu(viewModel.showDevicesMenu, viewModel)
            }
        }
    ) {
        Surface(modifier = Modifier.padding(it)) {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.4f),
                    shape = RoundedCornerShape(20.dp),
                ) {
                    val scrollState = rememberLazyListState()

                    LazyColumn(
                        state = scrollState,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        item {
                            Text(
                                text = viewModel.logText.value,
                                style = ConsoleStyle
                            )
                        }
                    }
                    LaunchedEffect(viewModel.logText.value) {
                        scrollState.animateScrollToItem(0)
                    }
                }

                Button(onClick = {
                    Shell.stop()
                }) {
                    Text("STOP")
                }
            }
        }
    }
}

@Composable
fun RebootMenu(expanded: MutableState<Boolean>, viewModel: ViewModel) {
    Box(
        modifier = Modifier
            .padding(3.dp)
    ) {
        IconButton(onClick = { expanded.value = !expanded.value }) {
            Icon(Icons.Filled.RestartAlt, contentDescription = stringResource(Res.string.reboot_menu))
        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(Res.string.system_to_fastboot)) },
                leadingIcon = { Icon(Icons.Filled.Build, contentDescription = null) },
                onClick = { /* Do something... */ }
            )
            DropdownMenuItem(
                text = { Text(stringResource(Res.string.system_to_recovery)) },
                leadingIcon = { Icon(painterResource(Res.drawable.ic_reset_wrench), contentDescription = null) },
                onClick = { /* Do something... */ }
            )

            HorizontalDivider()

            DropdownMenuItem(
                text = { Text(stringResource(Res.string.fastboot_to_system)) },
                leadingIcon = { Icon(Icons.Filled.Android, contentDescription = null) },
                onClick = { /* Do something... */ }
            )

            DropdownMenuItem(
                text = { Text(stringResource(Res.string.fastboot_to_recovery)) },
                leadingIcon = { Icon(painterResource(Res.drawable.ic_reset_wrench), contentDescription = null) },
                onClick = { /* Do something... */ }
            )
        }
    }
}

@Composable
fun FlashMenu(expanded: MutableState<Boolean>, viewModel: ViewModel) {
    Box(
        modifier = Modifier
            .padding(3.dp)
    ) {
        IconButton(onClick = { expanded.value = !expanded.value }) {
            Icon(Icons.Filled.BuildCircle, contentDescription = stringResource(Res.string.flash_menu))
        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(Res.string.flash_boot)) },
                leadingIcon = { Icon(Icons.Filled.Terminal, contentDescription = null) },
                onClick = { /* Do something... */ }
            )
            DropdownMenuItem(
                text = { Text(stringResource(Res.string.flash_gsi)) },
                leadingIcon = { Icon(Icons.Filled.Android, contentDescription = null) },
                onClick = { /* Do something... */ }
            )
        }
    }
}

@Composable
fun DeviceMenu(expanded: MutableState<Boolean>, viewModel: ViewModel) {
    Box(
        modifier = Modifier
            .padding(3.dp)
    ) {
        IconButton(onClick = { expanded.value = !expanded.value }) {
            Icon(Icons.Filled.Info, contentDescription = stringResource(Res.string.reboot_menu))
        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(Res.string.adb_devices)) },
                leadingIcon = { Icon(Icons.Filled.Terminal, contentDescription = null) },
                onClick = { viewModel.adbDevices() }
            )
            DropdownMenuItem(
                text = { Text(stringResource(Res.string.fastboot_devices)) },
                leadingIcon = { Icon(Icons.Filled.Android, contentDescription = null) },
                onClick = { viewModel.fastbootDevices() }
            )
        }
    }
}
