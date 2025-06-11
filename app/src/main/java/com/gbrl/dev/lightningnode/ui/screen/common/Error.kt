package com.gbrl.dev.lightningnode.ui.screen.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gbrl.dev.lightningnode.R
import com.gbrl.dev.lightningnode.ui.components.core.button.Button
import com.gbrl.dev.lightningnode.ui.components.core.button.ButtonType
import com.gbrl.dev.lightningnode.ui.theme.Colors

@Composable
fun Error(
    title: String,
    throwable: Throwable,
    primaryButtonText: String,
    onPrimaryButtonClick: (() -> Unit)? = null
) {
    Error(
        title = title,
        description = throwable.message,
        primaryButtonText = primaryButtonText,
        onPrimaryButtonClick = onPrimaryButtonClick,
        secondaryButtonText = null,
        onSecondaryButtonClick = null
    )
}

@Composable
fun Error(
    title: String = stringResource(R.string.error_title),
    description: String? = null,
    primaryButtonText: String = stringResource(R.string.error_try_again_text),
    onPrimaryButtonClick: (() -> Unit)? = null,
    secondaryButtonText: String? = null,
    onSecondaryButtonClick: (() -> Unit)? = null,
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box {
            Icon(
                modifier = Modifier.size(60.dp),
                painter = painterResource(R.drawable.ic_warning),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }

        Text(
            modifier = Modifier
                .padding(top = 16.dp),
            text = title.ifEmpty { stringResource(R.string.error_title) },
            fontWeight = FontWeight.Bold,
            color = Colors.darkGray500
        )

        description?.let {
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = description.ifEmpty { stringResource(R.string.error_description) },
                color = Colors.darkGray300
            )
        }

        Button(
            modifier = Modifier.padding(top = 16.dp),
            label = primaryButtonText,
            type = ButtonType.PRIMARY,
        ) {
            onPrimaryButtonClick?.invoke()
        }

        secondaryButtonText?.let {
            Button(
                modifier = Modifier.padding(top = 8.dp),
                label = it,
                type = ButtonType.SECONDARY
            ) {
                onSecondaryButtonClick?.invoke()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Error(
        title = stringResource(R.string.error_title),
        description = stringResource(R.string.error_description)
    )
}