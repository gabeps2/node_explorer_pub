package com.gbrl.dev.lightningnode.ui.components.core.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gbrl.dev.lightningnode.ui.theme.Colors
import androidx.compose.material3.Button as Material3Button

@Composable
fun Button(
    modifier: Modifier = Modifier,
    label: String,
    type: ButtonType,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(12.dp)
    val border = when (type) {
        ButtonType.PRIMARY,
        ButtonType.SECONDARY -> BorderStroke(
            width = 1.dp,
            color = Colors.lightBlue500
        )
    }

    val colors = when (type) {
        ButtonType.PRIMARY -> ButtonColors(
            containerColor = Colors.lightBlue500,
            contentColor = Colors.lightGray200,
            disabledContainerColor = Colors.lightGray300,
            disabledContentColor = Colors.white
        )

        ButtonType.SECONDARY -> ButtonColors(
            containerColor = Colors.white,
            contentColor = Colors.lightBlue500,
            disabledContainerColor = Colors.lightGray300,
            disabledContentColor = Colors.white
        )
    }

    val content: @Composable (RowScope.() -> Unit) = @Composable {
        Text(
            modifier = Modifier,
            text = label,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }

    Box(
        modifier = modifier
    ) {
        Material3Button(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth(),
            onClick = onClick,
            enabled = enabled,
            shape = shape,
            colors = colors,
            border = border,
            content = content
        )
    }
}

@Preview
@Composable
private fun ButtonPreview() {
    Column {
        Button(
            label = "Button",
            type = ButtonType.PRIMARY,
            onClick = {}
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            label = "Button",
            type = ButtonType.SECONDARY,
            onClick = {},
        )
    }
}