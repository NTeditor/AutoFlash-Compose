package screen.main

import Shell
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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
                RebootMenu(viewModel)
                FlashMenu(viewModel)
                DeviceMenu(viewModel)
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
                        .height(300.dp),
                    shape = RoundedCornerShape(20.dp),
                ) {
                    val scrollState = rememberLazyListState()

                    LazyColumn(
                        state = scrollState,
                        modifier = Modifier.padding(15.dp).fillMaxSize()
                    ) {
                        items(viewModel.logText) { text ->
                            Text(text = text)
                        }
                    }
                    LaunchedEffect(viewModel.logText.size) {
                        if (viewModel.logText.isNotEmpty()) {
                            scrollState.animateScrollToItem(viewModel.logText.size)
                        }
                    }
                }

                Button(onClick = {
                    Shell.stop()
                }, enabled = !viewModel.enableButton) {
                    Text(stringResource(Res.string.cancel))
                }
            }
        }
    }
}

@Composable
fun RebootMenu(viewModel: ViewModel) {
    Box(
        modifier = Modifier
            .padding(3.dp)
    ) {
        IconButton(onClick = { viewModel.updateUI(showRebootMenu = true) }, enabled = viewModel.enableButton) {
            Icon(Icons.Filled.RestartAlt, contentDescription = null)
        }
        DropdownMenu(
            expanded = viewModel.showRebootMenu,
            onDismissRequest = { viewModel.updateUI(showRebootMenu = false) }
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(Res.string.system_to_fastboot)) },
                leadingIcon = { Icon(Icons.Filled.Build, contentDescription = null) },
                onClick = {
                    viewModel.updateUI(showRebootMenu = false)
                    viewModel.rebootS2("fastboot")
                }
            )
            DropdownMenuItem(
                text = { Text(stringResource(Res.string.system_to_recovery)) },
                leadingIcon = { Icon(painterResource(Res.drawable.ic_reset_wrench), contentDescription = null) },
                onClick = {
                    viewModel.updateUI(showRebootMenu = false)
                    viewModel.rebootS2("recovery")
                }
            )

            HorizontalDivider()

            DropdownMenuItem(
                text = { Text(stringResource(Res.string.fastboot_to_system)) },
                leadingIcon = { Icon(Icons.Filled.Android, contentDescription = null) },
                onClick = {
                    viewModel.updateUI(showRebootMenu = false)
                    viewModel.rebootF2("")
                }
            )

            DropdownMenuItem(
                text = { Text(stringResource(Res.string.fastboot_to_recovery)) },
                leadingIcon = { Icon(painterResource(Res.drawable.ic_reset_wrench), contentDescription = null) },
                onClick = {
                    viewModel.updateUI(showRebootMenu = false)
                    viewModel.rebootF2("recovery")
                }
            )
        }
    }
}

@Composable
fun FlashMenu(viewModel: ViewModel) {
    Box(
        modifier = Modifier
            .padding(3.dp)
    ) {
        IconButton(onClick = { viewModel.updateUI(showFlashMenu = true) }, enabled = viewModel.enableButton) {
            Icon(Icons.Filled.BuildCircle, contentDescription = null)
        }
        DropdownMenu(
            expanded = viewModel.showFlashMenu,
            onDismissRequest = { viewModel.updateUI(showFlashMenu = false) }
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(Res.string.flash_boot)) },
                leadingIcon = { Icon(Icons.Filled.Terminal, contentDescription = null) },
                onClick = {
                    viewModel.updateUI(showFlashMenu = false)
                    viewModel.flashBoot()
                }
            )
            DropdownMenuItem(
                text = { Text(stringResource(Res.string.flash_gsi)) },
                leadingIcon = { Icon(Icons.Filled.Android, contentDescription = null) },
                onClick = {
                    viewModel.updateUI(showFlashMenu = false)
                    viewModel.flashGSI()
                }
            )
        }
    }
}

@Composable
fun DeviceMenu(viewModel: ViewModel) {
    Box(
        modifier = Modifier
            .padding(3.dp)
    ) {
        IconButton(onClick = { viewModel.updateUI(showDevicesMenu = true) }, enabled = viewModel.enableButton) {
            Icon(Icons.Filled.Info, contentDescription = null)
        }
        DropdownMenu(
            expanded = viewModel.showDevicesMenu,
            onDismissRequest = { viewModel.updateUI(showDevicesMenu = false) }
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(Res.string.adb_devices)) },
                leadingIcon = { Icon(Icons.Filled.Terminal, contentDescription = null) },
                onClick = {
                    viewModel.updateUI(showDevicesMenu = false)
                    viewModel.adbDevices()
                }
            )
            DropdownMenuItem(
                text = { Text(stringResource(Res.string.fastboot_devices)) },
                leadingIcon = { Icon(Icons.Filled.Android, contentDescription = null) },
                onClick = {
                    viewModel.updateUI(showDevicesMenu = false)
                    viewModel.fastbootDevices()
                }
            )
        }
    }
}
