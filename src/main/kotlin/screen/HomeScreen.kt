package screen

import Shell
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import androidx.compose.ui.graphics.Color
import screen.viewmodel.HomeViewModel
import ui.theme.ConsoleStyle

@Composable
fun HomeScreen() {
    val viewModel = viewModel<HomeViewModel>()
    HomeView(
        viewModel = viewModel
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    viewModel: HomeViewModel
) {
    @Composable
    fun DevicesMenuItems() {
        DropdownMenuItem(
            text = {
                Text("Подключенные устройства по ADB")
            },
            onClick = {
                viewModel.devicesMenuOpen.value = false
                viewModel.adbDevices()
            },
        )
        DropdownMenuItem(
            text = {
                Text("Подключенные устройства по Fastboot")
            },
            onClick = {
                viewModel.devicesMenuOpen.value = false
                viewModel.fastbootDevices()
            },
        )
    }

    val launcher = rememberFilePickerLauncher { file ->

    }

    @Composable
    fun RebootMenuItems() {
        DropdownMenuItem(
            text = {
                Text("System -> Fastboot")
            },
            onClick = {
                viewModel.rebootS2("fastboot")
                viewModel.rebootMenuOpen.value = false
            }
        )
        DropdownMenuItem(
            text = {
                Text("System -> Recovery")
            },
            onClick = {
                viewModel.rebootS2("recovery")
                viewModel.rebootMenuOpen.value = false
            }
        )
        DropdownMenuItem(
            text = {
                Text("Fastboot -> System")
            },
            onClick = {
                viewModel.rebootF2("")
                viewModel.rebootMenuOpen.value = false
            }
        )
        DropdownMenuItem(
            text = {
                Text("Fastboot -> Recovery")
            },
            onClick = {
                viewModel.rebootF2("recovery")
                viewModel.rebootMenuOpen.value = false
            }
        )
    }

    @Composable
    fun FlashMenuItems() {
        DropdownMenuItem(
            text = {
                Text("Boot")
            },
            onClick = {
                launcher.launch()
                viewModel.flashBoot()
                viewModel.flashMenuOpen.value = false
            }
        )
        DropdownMenuItem(
            text = {
                Text("GSI")
            },
            onClick = {
                viewModel.flashGSI()
                viewModel.flashMenuOpen.value = false
            }
        )
    }



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AutoFlash") },
                actions = {
                    Box {
                        TextButton(
                            onClick = {
                                viewModel.devicesMenuOpen.value = true
                            }) {
                            Text("Устройства")
                        }
                        DropdownMenu(
                            expanded = viewModel.devicesMenuOpen.value,
                            onDismissRequest = { viewModel.devicesMenuOpen.value = false }
                        ) {
                            DevicesMenuItems()
                        }
                    }
                    Box {
                        TextButton(
                            onClick = {
                                viewModel.rebootMenuOpen.value = true
                            }) {
                            Text("Перезагрузить")
                        }
                        DropdownMenu(
                            expanded = viewModel.rebootMenuOpen.value,
                            onDismissRequest = { viewModel.rebootMenuOpen.value = false },
                        ) {
                            RebootMenuItems()
                        }
                    }
                    Box {
                        TextButton(
                            onClick = {
                                viewModel.flashMenuOpen.value = true
                            }) {
                            Text("Прошить")
                        }
                        DropdownMenu(
                            expanded = viewModel.flashMenuOpen.value,
                            onDismissRequest = { viewModel.flashMenuOpen.value = false },
                        ) {
                            FlashMenuItems()
                        }
                    }
                }
            )
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

                    // Автопрокрутка вниз при новых записях
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