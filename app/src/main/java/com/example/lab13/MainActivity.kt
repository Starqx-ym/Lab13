package com.example.lab13

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.lab13.ui.theme.Lab13Theme

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith

// 👇 ESTE ES EL ÚNICO ScreenState válido
enum class ScreenState { LOADING, CONTENT, ERROR }

@OptIn(ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab13Theme {
                AnimatedContentStatesExample()
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentStatesExample() {

    var currentState by remember { mutableStateOf(ScreenState.LOADING) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AnimatedContent(
            targetState = currentState,
            transitionSpec = {
                fadeIn(animationSpec = tween(600)) togetherWith
                        fadeOut(animationSpec = tween(600))
            },
            label = "contentAnimation"
        ) { state ->

            when (state) {
                ScreenState.LOADING -> {
                    Text("Cargando...", color = Color.Gray)
                }
                ScreenState.CONTENT -> {
                    Text("Contenido cargado ", color = Color(0xFF1B5E20))
                }
                ScreenState.ERROR -> {
                    Text("Error al cargar ", color = Color(0xFFB71C1C))
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            currentState = when (currentState) {
                ScreenState.LOADING -> ScreenState.CONTENT
                ScreenState.CONTENT -> ScreenState.ERROR
                ScreenState.ERROR -> ScreenState.LOADING
            }
        }) {
            Text("Cambiar estado")
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
@Composable
fun SizeAndShapeAnimationExample() {

    var expanded by remember { mutableStateOf(false) }

    val size by animateDpAsState(
        targetValue = if (expanded) 200.dp else 120.dp,
        animationSpec = tween(durationMillis = 600),
        label = "sizeAnimation"
    )

    val cornerRadius by animateDpAsState(
        targetValue = if (expanded) 32.dp else 4.dp,
        animationSpec = tween(durationMillis = 600),
        label = "cornerAnimation"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier
                .size(size)
                .background(
                    color = Color(0xFF1976D2),
                    shape = RoundedCornerShape(cornerRadius)
                )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { expanded = !expanded }) {
            Text(if (expanded) "Reducir" else "Expandir")
        }
    }
}
