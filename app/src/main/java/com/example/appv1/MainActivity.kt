package com.example.appv1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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

    if (showMainScreen) {
        MainScreen(onBackClick = { showMainScreen = false })
    } else {
        InitialScreen(onNavigateToMain = { showMainScreen = true })
    }
}

@Composable
fun InitialScreen(onNavigateToMain: () -> Unit) {
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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to\nKeep Walking",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B5E20), // Nuevo tono de verde
                modifier = Modifier.padding(bottom = 32.dp),
                textAlign = TextAlign.Center
            )

            Image(
                painter = painterResource(id = R.drawable.imagenprincipalapp),
                contentDescription = "Imagen principal de la aplicación",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp) // Reducir el tamaño de la imagen
                    .padding(bottom = 32.dp)
            )

            ButtonContainer(text = "Conectar Bluetooth", color = buttonColor) {
                // TODO: Agregar la lógica de conexión Bluetooth
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Button(
                onClick = onNavigateToMain,
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                modifier = Modifier.size(100.dp, 50.dp) // Tamaño reducido para el botón "Saltar"
            ) {
                Text(
                    text = "Saltar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier, onBackClick: () -> Unit) {
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
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        Image(
            painter = painterResource(id = R.drawable.personasaludable),
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

        ButtonContainer(
            text = "Apagar",
            color = buttonColor,
            icon = Icons.Default.PowerSettingsNew,
            onClick = { isOn = false }
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
fun ButtonContainer(text: String, color: Color, icon: ImageVector? = null, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(color = color, shape = RoundedCornerShape(8.dp))
            .clickable(onClick = onClick) // Hacer todo el Box clickeable
            .padding(16.dp),
        contentAlignment = Alignment.Center
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
                fontSize = 18.sp,
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
        InitialScreen(onNavigateToMain = {})
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AppV1Theme {
        MainScreen(onBackClick = {})
    }
}