package com.example.lab13

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.lab13.ui.theme.Lab13Theme

@OptIn(ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab13Theme {
                ColorAnimationExample()
            }
        }
    }
}
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedVisibilityExampleScreen() {
    var visible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { visible = !visible }) {
            Text(if (visible) "Ocultar cuadro" else "Mostrar cuadro")
        }

        Spacer(modifier = Modifier.height(24.dp))

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(durationMillis = 500)) + slideInVertically(initialOffsetY = { -40 }, animationSpec = tween(500)),
            exit = fadeOut(animationSpec = tween(durationMillis = 400)) + slideOutVertically(targetOffsetY = { -40 }, animationSpec = tween(400))
        ) {
            Box(
                modifier = Modifier
                    .size(180.dp)
                    .background(Color(0xFF1976D2)) // azul
            ) {
                // Contenido del cuadro
                Text(
                    "¡Hola!",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }
        }
    }
}
@Composable
fun ColorAnimationExample() {
    var isOn by remember { mutableStateOf(false) }

    // Estado animado del color
    val backgroundColor by animateColorAsState(
        targetValue = if (isOn) Color(0xFF4CAF50) else Color(0xFFB71C1C),
        animationSpec = tween(durationMillis = 600),
        label = "colorAnimation"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(200.dp)
                .background(backgroundColor)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { isOn = !isOn }) {
            Text(if (isOn) "Cambiar a ROJO" else "Cambiar a VERDE")
        }
    }
}

