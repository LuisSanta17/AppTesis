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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appv1.ui.theme.AppV1Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

    when {
        showLogScreen -> LogScreen(onBackClick = { showLogScreen = false })
        showStimulusScreen -> StimulusScreen(onBackClick = { showStimulusScreen = false })
        showMainScreen -> MainScreen(
            onBackClick = { showMainScreen = false },
            onNavigateToStimulus = { showStimulusScreen = true }
        )
        else -> InitialScreen(
            onNavigateToMain = { showMainScreen = true },
            onNavigateToLog = { showLogScreen = true }
        )
    }
}

// Define constants for common values
private val ButtonColor = Color(0xFF4CAF50)
private val BackgroundColor = Color.White
private val GreenTextColor = Color(0xFF1B5E20)

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
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar()
            Spacer(modifier = Modifier.height(16.dp))

            WelcomeText()
            Spacer(modifier = Modifier.height(16.dp))

            PersonImage()
            Spacer(modifier = Modifier.height(32.dp))

            ButtonContainer(
                text = "Iniciar",
                color = ButtonColor,
                icon = Icons.Default.PlayArrow,
                onClick = onNavigateToMain
            )
            Spacer(modifier = Modifier.height(16.dp))

            ButtonContainer(
                text = "Conectar\nBluetooth",
                color = ButtonColor,
                icon = Icons.Default.Bluetooth,
                onClick = { /* TODO: Add Bluetooth connection logic */ }
            )
            Spacer(modifier = Modifier.height(16.dp))

            ButtonContainer(
                text = "Registro de Tiempo de Estímulos",
                color = ButtonColor,
                icon = Icons.Default.List,
                onClick = onNavigateToLog
            )
        }
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun WelcomeText() {
    Text(
        text = "Welcome to\nKeep Walking",
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = GreenTextColor,
        modifier = Modifier.padding(bottom = 32.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun PersonImage() {
    Image(
        painter = painterResource(id = R.drawable.personacaminando),
        contentDescription = "Imagen de persona caminando",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(bottom = 32.dp)
    )
}

@Composable
fun MainScreen(modifier: Modifier = Modifier, onBackClick: () -> Unit, onNavigateToStimulus: () -> Unit) {
    val buttonColor = ButtonColor
    val backgroundColor = BackgroundColor
    var mode by remember { mutableStateOf(1) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        TopBarWithBackButton(onBackClick)
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Controlador de Estimulador Muscular",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = GreenTextColor,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        Image(
            painter = painterResource(id = R.drawable.saludable),
            contentDescription = "Imagen de persona saludable",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(bottom = 16.dp)
        )

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
            text = "Agregar Tiempo de Estimulo",
            color = buttonColor,
            icon = Icons.Default.Add,
            onClick = onNavigateToStimulus
        )
        Spacer(modifier = Modifier.height(16.dp))

        ButtonContainer(
            text = "Seleccionar Pierna",
            color = buttonColor,
            icon = Icons.Default.DirectionsRun,
            onClick = { /* TODO: Implement leg selection logic */ }
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
    }
}

@Composable
fun TopBarWithBackButton(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Retroceder",
                tint = ButtonColor
            )
        }
    }
}

@Composable
fun StimulusScreen(onBackClick: () -> Unit) {
    val buttonColor = ButtonColor
    var isRunning by remember { mutableStateOf(false) }
    var elapsedTime by remember { mutableStateOf(0L) }
    var mode by remember { mutableStateOf(1) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(isRunning) {
        while (isRunning) {
            delay(10L)
            elapsedTime += 10
        }
    }

    val minutes = (elapsedTime / 60000) % 60
    val seconds = (elapsedTime / 1000) % 60
    val milliseconds = (elapsedTime / 10) % 100

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBarWithBackButton(onBackClick)
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Tiempo de Estimulo",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = GreenTextColor,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        Image(
            painter = painterResource(id = R.drawable.cronometro),
            contentDescription = "Imagen de cronómetro",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(bottom = 16.dp)
        )

        Box(
            modifier = Modifier
                .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Text(
                text = String.format("%02d:%02d.%02d", minutes, seconds, milliseconds),
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        ButtonContainer(
            text = if (isRunning) "Pausar" else "Iniciar",
            color = buttonColor,
            icon = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
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
    }
}

@Composable
fun LogScreen(onBackClick: () -> Unit) {
    val buttonColor = ButtonColor

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBarWithBackButton(onBackClick)
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Registro de Tiempo de Estímulos",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = GreenTextColor,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        Image(
            painter = painterResource(id = R.drawable.cronometro),
            contentDescription = "Imagen de registro",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(bottom = 16.dp)
        )

        // Aquí podrías agregar una lista de registros si tuvieras una
    }
}

@Composable
fun ButtonContainer(text: String, color: Color, icon: ImageVector? = null, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InitialScreenPreview() {
    AppV1Theme {
        InitialScreen(onNavigateToMain = {}, onNavigateToLog = {})
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AppV1Theme {
        MainScreen(onBackClick = {}, onNavigateToStimulus = {})
    }
}

@Preview(showBackground = true)
@Composable
fun StimulusScreenPreview() {
    AppV1Theme {
        StimulusScreen(onBackClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun LogScreenPreview() {
    AppV1Theme {
        LogScreen(onBackClick = {})
    }
}
