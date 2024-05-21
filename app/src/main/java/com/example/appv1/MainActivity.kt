package com.example.appv1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        enableEdgeToEdge()
        setContent {
            AppV1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val buttonColor = Color(0xFF4CAF50) // Color verde para los botones
    val backgroundColor = Color(0xFFFFFFFF) // Color de fondo blanco

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Estimulador Muscular",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1B5E20),
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        Image(
            painter = painterResource(id = R.drawable.imagenprincipalapp),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(bottom = 32.dp)
        )

        ButtonContainer(text = "Encendido", color = buttonColor) {
            // Acción para Encendido
        }

        ButtonContainer(text = "Apagado", color = buttonColor) {
            // Acción para Apagado
        }

        ButtonContainer(text = "Modo", color = buttonColor) {
            // Acción para Modo
        }
    }
}

@Composable
fun ButtonContainer(text: String, color: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(color = color, shape = RoundedCornerShape(8.dp))
            .clickable(onClick = onClick) // Hacer todo el Box clickeable
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AppV1Theme {
        MainScreen()
    }
}
