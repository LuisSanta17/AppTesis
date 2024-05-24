package com.example.appv1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appv1.ui.theme.AppV1Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class StimulusLog(val time: String, val mode: Int, val name: String, val leg: Leg)

enum class Leg {
    Izquierda, Derecha
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppV1Theme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    var showMainScreen by remember { mutableStateOf(false) }
    var showStimulusScreen by remember { mutableStateOf(false) }
    var showLogScreen by remember { mutableStateOf(false) }
    var logs by remember { mutableStateOf(listOf<StimulusLog>()) }

    when {
        showLogScreen -> LogScreen(
            logs,
            onBackClick = { showLogScreen = false },
            onAddStimulusClick = {
                showLogScreen = false
                showStimulusScreen = true
            }
        )
        showStimulusScreen -> StimulusScreen(
            onBackClick = {
                showStimulusScreen = false
                showLogScreen = true
            }
        ) { log ->
            logs = logs + log
            showStimulusScreen = false
            showLogScreen = true
        }
        showMainScreen -> MainScreen(
            onBackClick = { showMainScreen = false },
            onNavigateToStimulus = { showStimulusScreen = true },
            onNavigateToLog = { showLogScreen = true }
        )
        else -> InitialScreen(
            onNavigateToMain = { showMainScreen = true },
            onNavigateToLog = { showLogScreen = true }
        )
    }
}

private val ButtonColor = Color(0xFF4CAF50)
private val BackgroundColor = Color(0xFFF5F5F5)
private val GreenTextColor = Color(0xFF1B5E20)
private val ContainerColor = Color(0xFFE8F5E9)
private val TextColor = Color.White

@Composable
fun InitialScreen(onNavigateToMain: () -> Unit, onNavigateToLog: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(title = "Welcome to\nKeep Walking")
            Spacer(modifier = Modifier.height(32.dp))

            PersonImage(imageRes = R.drawable.imagenprincipalapp)
            Spacer(modifier = Modifier.height(32.dp))

            ButtonContainer(
                text = "Iniciar",
                color = ButtonColor,
                icon = Icons.Default.PlayArrow,
                onClick = onNavigateToMain,
                height = 56.dp // Slightly larger height
            )
            Spacer(modifier = Modifier.height(16.dp))

            ButtonContainer(
                text = "Conectar\nBluetooth",
                color = ButtonColor,
                icon = Icons.Default.Bluetooth,
                onClick = { /* TODO: Add Bluetooth connection logic */ },
                height = 56.dp // Slightly larger height
            )
            Spacer(modifier = Modifier.height(16.dp))

            ButtonContainer(
                text = "Registro de\nTiempo",
                color = ButtonColor,
                icon = Icons.Default.List,
                onClick = onNavigateToLog,
                height = 56.dp // Slightly larger height
            )
        }
    }
}

@Composable
fun TopBar(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(ButtonColor)
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = TextColor,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun PersonImage(imageRes: Int) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(bottom = 16.dp)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Imagen",
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier, onBackClick: () -> Unit, onNavigateToStimulus: () -> Unit, onNavigateToLog: () -> Unit) {
    val buttonColor = ButtonColor
    val backgroundColor = BackgroundColor
    var mode by remember { mutableStateOf(1) }
    var selectedLeg by remember { mutableStateOf<Leg?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .padding(8.dp)
                .size(24.dp) // Reduced size
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Retroceder",
                tint = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        TopBar(title = "Controlador de Estimulador Muscular")

        Spacer(modifier = Modifier.height(16.dp))

        PersonImage(imageRes = R.drawable.personacaminando)

        var isOn by remember { mutableStateOf(false) }

        ButtonContainer(
            text = "Encender",
            color = buttonColor,
            icon = Icons.Default.PowerSettingsNew,
            onClick = { isOn = true }
        )
        Spacer(modifier = Modifier.height(16.dp))

        ButtonContainer(
            text = "Apagar",
            color = buttonColor,
            icon = Icons.Default.PowerSettingsNew,
            onClick = { isOn = false }
        )
        Spacer(modifier = Modifier.height(16.dp))

        ButtonContainer(
            text = "Seleccionar Pierna: ${selectedLeg?.toString() ?: "Ninguna"}",
            color = buttonColor,
            icon = Icons.Default.DirectionsRun,
            onClick = { selectedLeg = if (selectedLeg == Leg.Izquierda) Leg.Derecha else Leg.Izquierda }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ButtonContainer(
                text = "Modo $mode",
                color = buttonColor,
                icon = Icons.Default.Settings,
                onClick = {
                    mode = (mode % 8) + 1
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        ButtonContainer(
            text = "Registro de Tiempo",
            color = buttonColor,
            icon = Icons.Default.List,
            onClick = onNavigateToLog
        )
    }
}

@Composable
fun StimulusScreen(onBackClick: () -> Unit, onSaveLog: (StimulusLog) -> Unit) {
    val buttonColor = ButtonColor
    var isRunning by remember { mutableStateOf(false) }
    var elapsedTime by remember { mutableStateOf(0L) }
    var mode by remember { mutableStateOf(1) }
    val scope = rememberCoroutineScope()
    var showNameInputDialog by remember { mutableStateOf(false) }
    var logName by remember { mutableStateOf("") }
    var selectedLeg by remember { mutableStateOf<Leg?>(null) }

    LaunchedEffect(isRunning) {
        while (isRunning) {
            delay(10L)
            elapsedTime += 10
        }
    }

    val minutes = (elapsedTime / 60000) % 60
    val seconds = (elapsedTime / 1000) % 60
    val milliseconds = (elapsedTime / 10) % 100

    if (showNameInputDialog) {
        AlertDialog(
            onDismissRequest = { showNameInputDialog = false },
            title = { Text("Guardar Registro") },
            text = {
                Column {
                    Text("Ingrese el nombre del registro:")
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = logName,
                        onValueChange = { logName = it },
                        placeholder = { Text("Nombre del registro") }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showNameInputDialog = false
                        val log = StimulusLog(
                            time = String.format("%02d:%02d:%02d", minutes, seconds, milliseconds),
                            mode = mode,
                            name = logName,
                            leg = selectedLeg ?: Leg.Izquierda
                        )
                        onSaveLog(log)
                    }
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                Button(onClick = { showNameInputDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .padding(8.dp)
                .size(24.dp) // Reduced size
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Retroceder",
                tint = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TopBar(title = "Agregar Tiempo de Estímulo")

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(ContainerColor, shape = RoundedCornerShape(16.dp))
        ) {
            Text(
                text = String.format("%02d:%02d:%02d", minutes, seconds, milliseconds),
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = GreenTextColor
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        ButtonContainer(
            text = if (isRunning) "Detener" else "Iniciar",
            color = buttonColor,
            icon = if (isRunning) Icons.Default.Stop else Icons.Default.PlayArrow,
            onClick = { isRunning = !isRunning }
        )
        Spacer(modifier = Modifier.height(16.dp))

        ButtonContainer(
            text = "Reiniciar",
            color = buttonColor,
            icon = Icons.Default.Refresh,
            onClick = {
                isRunning = false
                elapsedTime = 0L
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        ButtonContainer(
            text = "Seleccionar Pierna: ${selectedLeg?.toString() ?: "Ninguna"}",
            color = buttonColor,
            icon = Icons.Default.DirectionsRun,
            onClick = { selectedLeg = if (selectedLeg == Leg.Izquierda) Leg.Derecha else Leg.Izquierda }
        )
        Spacer(modifier = Modifier.height(16.dp))

        ButtonContainer(
            text = "Modo $mode",
            color = buttonColor,
            icon = Icons.Default.Settings,
            onClick = {
                mode = (mode % 8) + 1
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        ButtonContainer(
            text = "Guardar Registro",
            color = buttonColor,
            icon = Icons.Default.Save,
            onClick = {
                isRunning = false
                showNameInputDialog = true
            }
        )
    }
}

@Composable
fun LogScreen(logs: List<StimulusLog>, onBackClick: () -> Unit, onAddStimulusClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .padding(8.dp)
                    .size(24.dp) // Reduced size
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Retroceder",
                    tint = Color.Black
                )
            }
            IconButton(
                onClick = onAddStimulusClick,
                modifier = Modifier
                    .padding(8.dp)
                    .size(24.dp) // Reduced size
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar Tiempo de Estímulo",
                    tint = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        TopBar(title = "Registro de Tiempos")

        Spacer(modifier = Modifier.height(16.dp))

        if (logs.isEmpty()) {
            Text(
                text = "No hay registros disponibles.",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        } else {
            Column {
                logs.forEach { log ->
                    StimulusLogItem(log)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun StimulusLogItem(log: StimulusLog) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(ContainerColor, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(text = "Nombre: ${log.name}", fontWeight = FontWeight.Bold)
        Text(text = "Tiempo: ${log.time}")
        Text(text = "Modo: ${log.mode}")
        Text(text = "Pierna: ${log.leg}")
    }
}

@Composable
fun ButtonContainer(text: String, color: Color, icon: ImageVector, onClick: () -> Unit, height: Dp = 48.dp) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(color),
        modifier = Modifier
            .fillMaxWidth()
            .height(height) // Adjustable height
            .padding(horizontal = 8.dp)
    ) {
        Icon(icon, contentDescription = text, modifier = Modifier.size(20.dp)) // Reduced icon size
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, fontSize = 14.sp, fontWeight = FontWeight.Bold) // Reduced text size
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppV1Theme {
        MyApp()
    }
}
