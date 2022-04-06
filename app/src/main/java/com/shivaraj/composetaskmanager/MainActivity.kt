package com.shivaraj.composetaskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val scaffoldState = rememberScaffoldState()
            var textState by remember {
                mutableStateOf("")
            }

            val scope = rememberCoroutineScope()

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                scaffoldState = scaffoldState
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 30.dp)

                ) {
                    TextField(
                        value = textState,
                        label = {
                                Text(text = "Enter name")
                        },
                        onValueChange = {
                            textState = it
                        },
                        
                    )
                    
                    Spacer(modifier = Modifier.height(30.dp))
                    Button(onClick = {
                        scope.launch {

                            scaffoldState.snackbarHostState.showSnackbar(
                                "Hello world $textState"
                            )
                        }
                    }) {
                        Text(text = "Show snack bar")
                    }

                }

            }

        }
    }
}

//Tutorial 2 START
@Composable
fun ColorBoxContainer() {
    Column(Modifier.fillMaxSize()) {
        val color = remember {
            mutableStateOf(Color.Yellow)
        }

        ColourBox(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ) {
            color.value = it
        }
        Box(
            modifier = Modifier
                .background(color = color.value)
                .weight(1f)
                .fillMaxSize()
        )
    }
}

@Composable
fun ColourBox(
    modifier: Modifier = Modifier,
    updateColor: (Color) -> Unit
) {
    Box(modifier = modifier
        .background(color = Color.Red)
        .clickable {
            updateColor(
                Color(
                    Random.nextFloat(),
                    Random.nextFloat(),
                    Random.nextFloat(),
                    1f
                )
            )

        }
    ) {

    }
}

//Tutorial 2 END


//Tutorial 1 START
@Composable
fun PlayWithText() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .padding(start = 16.dp, top = 16.dp)
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Green,
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Cursive,

                        )
                ) {
                    append("Shivaraj ")
                }
                withStyle(
                    style = SpanStyle(
                        color = Color.Red,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("Patil")
                }
            },
            color = Color.Red,
            fontSize = 20.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

//Tutorial 1 END

//Tutorial 0 START

@Composable
fun ImageCard(
    painter: Painter,
    contentDesc: String,
    title: String,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = CutCornerShape(8.dp),
        elevation = 4.dp
    ) {

        Box(
            modifier = Modifier.height(200.dp),

            ) {

            Image(
                painter = painter,
                contentDescription = contentDesc,
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black,
                            ),
                            startY = 300f
                        )
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    title,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp
                    )
                )
            }

        }
    }

}
//Tutorial 0 END