package com.shivaraj.composetaskmanager

import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.roundToInt

class ComposeVolumeButton : ComponentActivity() {
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.background(Color.Black)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .border(1.dp, Color.Green, RoundedCornerShape(10.dp))
                ) {
                    var volume by remember {
                        mutableStateOf(0f)
                    }
                    val barcount = 20
                    MusicKnob(
                        modifier = Modifier.size(100.dp),
                    ) {
                        volume = it
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    VolumeBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .padding(bottom = 8.dp),
                        activeBars = (barcount * volume).roundToInt(),
                        barCount = barcount
                    )

                }
            }
        }
    }
}


@Composable
fun VolumeBar(
    modifier: Modifier = Modifier,
    activeBars: Int = 0,
    barCount: Int = 10
) {

    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {

        val barWidth = remember {
            constraints.maxWidth / (2f * barCount)
        }

        Canvas(
            modifier = modifier.padding(bottom = 8.dp)
        ) {
            for (i in 0..barCount) {
                drawRoundRect(
                    color = (if (i in 0..activeBars) {
                        Color.Green
                    } else {
                        Color.Gray
                    }),
                    topLeft = Offset(i * barWidth * 2f + barWidth / 2f, 0f),
                    size = Size(barWidth, constraints.maxWidth.toFloat()),
                    cornerRadius = CornerRadius(0f),
                )
            }
        }

    }

}

@ExperimentalComposeUiApi
@Composable
fun MusicKnob(
    modifier: Modifier = Modifier,
    limitingAngle: Float = 25f,
    onValueChange: (Float) -> Unit
) {
    var rotation by remember {
        mutableStateOf(limitingAngle)
    }

    var touchX by remember {
        mutableStateOf(0f)
    }

    var touchY by remember {
        mutableStateOf(0f)
    }

    var centerX by remember {
        mutableStateOf(0f)
    }

    var centerY by remember {
        mutableStateOf(0f)
    }

    Image(
        painter = painterResource(id = R.drawable.music),
        contentDescription = "Music Knob",
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned {
                centerX = it.boundsInWindow().size.width / 2f
                centerY = it.boundsInWindow().size.height / 2f
            }
            .pointerInteropFilter {
                touchX = it.x
                touchY = it.y

                val angle = -atan2(centerX - touchX, centerY - touchY) * (180f / PI).toFloat()

                when (it.action) {
                    MotionEvent.ACTION_DOWN,
                    MotionEvent.ACTION_MOVE -> {
                        if (angle !in -limitingAngle..limitingAngle) {
                            val fixedAngle = if (angle in -180f..-limitingAngle) {
                                360f + angle
                            } else {
                                angle
                            }
                            rotation = fixedAngle
                            val percent = (fixedAngle - limitingAngle) / (360f - 2 * limitingAngle)
                            onValueChange(percent)
                            true
                        } else {
                            false
                        }
                    }
                    else -> false
                }

            }
            .rotate(rotation)
    )

}