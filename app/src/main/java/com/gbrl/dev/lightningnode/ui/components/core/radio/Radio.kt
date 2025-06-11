package com.gbrl.dev.lightningnode.ui.components.core.radio

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gbrl.dev.lightningnode.ui.theme.Colors
import androidx.compose.material3.Button as Material3Button

@Composable
fun Radio(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    label: String,
    type: RadioType,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(0.dp)

    val colors = when (type) {
        RadioType.PRIMARY -> ButtonColors(
            containerColor = Colors.lightBlue500,
            contentColor = Colors.lightGray200,
            disabledContainerColor = Colors.lightGray300,
            disabledContentColor = Colors.white
        )

        RadioType.SECONDARY -> ButtonColors(
            containerColor = Colors.lightGray200,
            contentColor = Colors.lightBlue500,
            disabledContainerColor = Colors.lightGray300,
            disabledContentColor = Colors.white
        )
    }

    val content: @Composable (RowScope.() -> Unit) = @Composable {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier,
                text = label,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )

            Canvas(modifier = Modifier.size(20.dp)) {
                val radius = size.minDimension / 2
                val color = when (type) {
                    RadioType.PRIMARY -> Colors.lightBlue100
                    RadioType.SECONDARY -> Colors.lightBlue500
                }

                val innerColor = when (type) {
                    RadioType.PRIMARY -> if (selected) Colors.lightBlue500 else Colors.lightBlue100
                    RadioType.SECONDARY -> if (selected) Colors.lightBlue100 else Colors.lightBlue500
                }

                drawCircle(
                    color = color,
                    radius = radius,
                    center = center,
                    style = if (selected) Fill else Stroke(width = 2.dp.toPx())
                )

                if (selected) {
                    drawCircle(
                        color = innerColor,
                        radius = radius / 3,
                        center = center
                    )
                }
            }
        }
    }

    Box(modifier = modifier) {
        Material3Button(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth(),
            onClick = onClick,
            enabled = enabled,
            shape = shape,
            colors = colors,
            content = content
        )
    }
}

@Preview
@Composable
private fun PreviewPrimary() {
    Column {
        Radio(
            label = "Radio",
            type = RadioType.PRIMARY,
            selected = true,
            onClick = {}
        )
        Spacer(Modifier.height(8.dp))
        Radio(
            label = "Radio",
            type = RadioType.PRIMARY,
            selected = false,
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun PreviewSecondary() {
    Column {
        Radio(
            label = "Radio",
            type = RadioType.SECONDARY,
            selected = true,
            onClick = {}
        )
        Spacer(Modifier.height(8.dp))
        Radio(
            label = "Radio",
            type = RadioType.SECONDARY,
            selected = false,
            onClick = {}
        )
    }
}