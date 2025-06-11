package com.gbrl.dev.lightningnode.ui.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.gbrl.dev.lightningnode.ui.theme.Colors

@Composable
fun SheetContainer(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            .fillMaxWidth()
            .background(Colors.lightGray200)
    ) {
        SheetHeader()
        content()
    }
}

@Composable
private fun SheetHeader() {
    Box(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Spacer(
            Modifier
                .width(48.dp)
                .height(4.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Colors.lightGray400)
        )
    }
}