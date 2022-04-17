@file:OptIn(ExperimentalAnimationApi::class)

package uz.umarxon.lazycolumcompose

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import uz.umarxon.lazycolumcompose.ui.theme.LazyColumComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyColumComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LazyColumnDemo()
                }
            }
        }
    }
}

@Composable
fun LazyColumnDemo() {
    val list = listOf(
        "A", "B", "C", "D"
    ) + ((0..100).map { it.toString() })
    LazyColumn(modifier = Modifier.fillMaxHeight()) {
        items(items = list, itemContent = { item ->
            when (item) {
                "A" -> {
                    MyCustomText(
                        item = item,
                        style = TextStyle(fontSize = 80.sp, color = Color.White),
                        color = Color.Cyan
                    )
                }
                "B" -> {
                    MyCustomText(
                        item = item,
                        style = TextStyle(fontSize = 80.sp, color = Color.Black),
                        color = Color.Red
                    )
                }
                "C" -> {
                    //Bu itemni o'tkazib yuborish
                    //Skip this item
                }
                "D" -> {
                    MyCustomText(
                        item = item,
                        style = TextStyle(fontSize = 80.sp, color = Color.Cyan),
                        color = Color.Black
                    )
                }
                else -> {
                    Spacer(modifier = Modifier.height(10.dp))
                    MyCustomText(
                        item = item,
                        style = TextStyle(fontSize = 80.sp, color = Color.Magenta),
                        color = Color.Yellow
                    )
                }
            }
        })
    }
}

@Composable
fun MyCustomText(item: String, style: TextStyle, color: Color) {

    val context = LocalContext.current
    val scaleA = remember { Animatable(initialValue = 1f) }

    LaunchedEffect(key1 = true) {
        launch {
            scaleA.animateTo(
                targetValue = 0.8f,
                animationSpec = tween(
                    durationMillis = 50
                )
            )
            scaleA.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }
    }
    Column(
        modifier = Modifier
            .padding(10.dp)
            .scale(scale = scaleA.value)
            .clip(RoundedCornerShape(20.dp))
            .clickable {
                showMessage(context = context, item)
            }
            .border(
                width = 3.dp,
                color = Color.Red,
                shape = RoundedCornerShape(20.dp)
            ),
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(color)
                .clip(RoundedCornerShape(20.dp)),
            text = item,
            style = style,
            textAlign = TextAlign.Center
        )
    }
}

fun showMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

/*
@Composable
fun MySnackbarView(item: String) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier,
        scaffoldState = scaffoldState // attaching `scaffoldState` to the `Scaffold`
    ) {
        Button(
            onClick = {
                coroutineScope.launch { // using the `coroutineScope` to `launch` showing the snackbar
                    // taking the `snackbarHostState` from the attached `scaffoldState`
                    val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                        message = item,
                        actionLabel = "OK"
                    )
                    when (snackbarResult) {
                        SnackbarResult.Dismissed -> Log.d(
                            "SnackbarView",
                            "O'tkazib yuborildi... Dismissed"
                        )
                        SnackbarResult.ActionPerformed -> Log.d("SnackbarView", "button clicked")
                    }
                }
            }
        ) {
            Text(text = "A button that shows a Snackbar")
        }
    }
}*/

@Preview
@Composable
fun Show() {
    LazyColumnDemo()
}

