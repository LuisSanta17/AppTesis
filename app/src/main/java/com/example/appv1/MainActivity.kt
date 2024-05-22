package com.example.appv1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Settings
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
        showLogScreen -> {
            LogScreen(onBackClick = { showLogScreen = false })
        }
        showStimulusScreen -> {
            StimulusScreen(onBackClick = { showStimulusScreen = false })
        }
        showMainScreen -> {
            MainScreen(
                onBackClick = { showMainScreen = false },
                onNavigateToStimulus = { showStimulusScreen = true }
            )
        }
        else -> {
            InitialScreen(
                onNavigateToMain = { showMainScreen = true },
                onNavigateToLog = { showLogScreen = true }
            )
        }
    }
}

@Composable
fun InitialScreen(onNavigateToMain: () -> Unit, onNavigateToLog: () -> Unit) {
    val buttonColor = Color(0xFF4CAF50) // Color verde para los botones

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = onNavigateToMain,
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(
                        text = "Skip",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Welcome to\nKeep Walking",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B5E20), // Nuevo tono de verde
                modifier = Modifier.padding(bottom = 32.dp),
                textAlign = TextAlign.Center
            )

            Image(
                painter = painterResource(id = R.drawable.personacaminando),
                contentDescription = "Imagen de persona caminando",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp) // Reducir el tamaño de la imagen
                    .padding(bottom = 32.dp)
            )

            Box(
                modifier = Modifier
                    .width(200.dp) // Ajustar el ancho del contenedor
            ) {
                ButtonContainer(text = "Conectar\nBluetooth", color = buttonColor) {
                    // TODO: Agregar la lógica de conexión Bluetooth
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onNavigateToLog,
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Registro de Tiempo de Estímulos",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier, onBackClick: () -> Unit, onNavigateToStimulus: () -> Unit) {
    val buttonColor = Color(0xFF4CAF50) // Color verde para los botones
    val backgroundColor = Color(0xFFFFFFFF) // Color de fondo blanco

    var mode by remember { mutableStateOf(1) } // Estado para el modo actual

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
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
                    tint = buttonColor
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Controlador de Estimulador Muscular",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1B5E20),
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
                .height(200.dp) // Reducir la altura de la imagen
                .padding(bottom = 16.dp)
        )

        // Button states
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
            icon = Icons.Default.Settings,
            onClick = onNavigateToStimulus
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
                    mode = (mode % 8) + 1 // Cambiar al siguiente modo, volviendo a 1 después de 8
                }
            )
        }
    }
}

@Composable
fun StimulusScreen(onBackClick: () -> Unit) {
    val buttonColor = Color(0xFF4CAF50) // Color verde para los botones
    var isRunning by remember { mutableStateOf(false) }
    var elapsedTime by remember { mutableStateOf(0L) }
    var mode by remember { mutableStateOf(1) } // Estado para el modo actual
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
                    tint = buttonColor
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Tiempo de Estimulo",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1B5E20),
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
                .height(200.dp) // Reducir la altura de la imagen
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
            onClick = { isRunning = !isRunning }
        )

        Spacer(modifier = Modifier.height(16.dp))

        ButtonContainer(
            text = "Reiniciar",
            color = buttonColor,
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
                    mode = (mode % 8) + 1 // Cambiar al siguiente modo, volviendo a 1 después de 8
                }
            )
        }
    }
}

@Composable
fun LogScreen(onBackClick: () -> Unit) {
    val buttonColor = Color(0xFF4CAF50) // Color verde para los botones

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                    tint = buttonColor
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Registro de Tiempo de Estímulos",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1B5E20),
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
                .height(200.dp) // Reducir la altura de la imagen
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
