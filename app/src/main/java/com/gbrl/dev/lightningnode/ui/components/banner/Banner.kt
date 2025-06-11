package com.gbrl.dev.lightningnode.ui.components.banner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gbrl.dev.lightningnode.R
import com.gbrl.dev.lightningnode.ui.components.core.button.Button
import com.gbrl.dev.lightningnode.ui.components.core.button.ButtonType
import com.gbrl.dev.lightningnode.ui.theme.Colors

@Composable
fun Banner(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    onButtonClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .background(Colors.lightBlue200)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold
        )

        description?.let {
            Text(
                text = it,
                color = Colors.darkGray300
            )
        }

        Button(
            modifier = Modifier.padding(top = 8.dp),
            label = stringResource(R.string.check_list_button),
            type = ButtonType.PRIMARY,
            onClick = onButtonClick
        )
    }
}
