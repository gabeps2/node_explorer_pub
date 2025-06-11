package com.gbrl.dev.lightningnode.ui.components.header

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gbrl.dev.lightningnode.R
import com.gbrl.dev.lightningnode.ui.theme.Colors

@Composable
fun Header(
    modifier: Modifier = Modifier,
    label: String,
    leftIcon:  @Composable (() -> Unit)? = null,
    onLeftIconClick: (() -> Unit)? = null,
    rightIcon: @Composable (() -> Unit)? = null,
    onRightIconClick: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Colors.lightGray200)
        ,
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        onLeftIconClick?.invoke()
                    },
                contentAlignment = Alignment.Center
            ) {
                leftIcon?.invoke()
            }

            Text(
                text = label,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = Colors.darkGray500
            )

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        onRightIconClick?.invoke()
                    },
                contentAlignment = Alignment.Center
            ) {
                rightIcon?.invoke()
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Header(
        label = "Node Explorer",
        leftIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_left),
                contentDescription = null,
                tint = Colors.darkGray500
            )
        }
    )
}

