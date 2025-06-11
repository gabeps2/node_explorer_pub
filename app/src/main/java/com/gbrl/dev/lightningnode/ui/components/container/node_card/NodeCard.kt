package com.gbrl.dev.lightningnode.ui.components.container.node_card

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gbrl.dev.lightningnode.R
import com.gbrl.dev.lightningnode.model.NodeUiModel
import com.gbrl.dev.lightningnode.ui.components.core.button.Button
import com.gbrl.dev.lightningnode.ui.components.core.button.ButtonType
import com.gbrl.dev.lightningnode.ui.test.semantic.drawableId
import com.gbrl.dev.lightningnode.ui.theme.Colors
import kotlinx.coroutines.launch

@Composable
fun NodeCard(
    modifier: Modifier = Modifier,
    node: NodeUiModel,
    onFavoriteClick: ((Boolean) -> Unit)? = null,
    position: Int? = null,
    nodeCardResizing: NodeCardResizing = NodeCardResizing.HUG,
    onDetailsClick: (() -> Unit)? = null
) {
    val width = when (nodeCardResizing) {
        NodeCardResizing.FILL -> Modifier.fillMaxWidth()
        NodeCardResizing.HUG -> Modifier.width(240.dp)
    }

    var isFavorite by remember { mutableStateOf(node.isFavorite) }

    val border = BorderStroke(
        width = 1.dp,
        color = Colors.lightGray300
    )

    val shape = RoundedCornerShape(8.dp)

    Card(
        modifier = modifier
            .then(width),
        border = border,
        shape = shape
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Colors.lightGray200),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Colors.lightBlue200)
                    .height(120.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.size(200.dp),
                        painter = painterResource(R.drawable.ic_lightning),
                        contentDescription = null,
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier.weight(1f),
                    ) {
                        PositionBadge(position)
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                    ) {
                        FavoriteIcon(
                            modifier = Modifier.align(Alignment.End),
                            isFavorite = isFavorite
                        ) {
                            isFavorite = !isFavorite
                            onFavoriteClick?.invoke(isFavorite)
                        }

                        PublicKeyIcon(
                            modifier = Modifier.align(Alignment.End),
                            node = node
                        ) { }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Colors.lightGray200)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = node.alias,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )

                Details(
                    key = stringResource(R.string.channels_title),
                    keyStyle = TextStyle.Default.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    ),
                    value = node.channels,
                    valueStyle = TextStyle.Default.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                )

                Details(
                    key = stringResource(R.string.capacity_title),
                    value = "${node.capacity} ${stringResource(R.string.btc)}"
                )

                Details(
                    key = stringResource(R.string.country_title),
                    value = node.country
                )

                Button(
                    modifier = Modifier.padding(top = 8.dp),
                    label = stringResource(R.string.check_details_button),
                    type = ButtonType.SECONDARY
                ) {
                    onDetailsClick?.invoke()
                }
            }
        }
    }
}


@Composable
private fun PositionBadge(
    position: Int? = null
) {
    position?.let {
        Box(
            modifier = Modifier
                .width(40.dp)
                .height(20.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Colors.lightBlue500),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$position${stringResource(R.string.ordinal_symbol)}",
                color = Colors.white,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun PublicKeyIcon(
    modifier: Modifier = Modifier,
    node: NodeUiModel,
    onClick: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    val copiedKeyActionText = stringResource(R.string.public_key_copied_action)

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .padding(top = 8.dp)
                .size(24.dp)
                .clickable {
                    onClick()
                    coroutineScope.launch {
                        clipboardManager.setText(AnnotatedString(node.publicKey))
                        Toast.makeText(
                            context,
                            "${node.alias} - $copiedKeyActionText",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
            painter = painterResource(
                id = R.drawable.ic_key
            ),
            contentDescription = null,
            tint = Colors.lightBlue500
        )
    }
}

@Composable
private fun FavoriteIcon(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    onClick: () -> Unit
) {
    val drawable = when (isFavorite) {
        true -> R.drawable.ic_heart_filled
        false -> R.drawable.ic_heart
    }

    Box(
        modifier = modifier
            .semantics { drawableId = drawable },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    onClick()
                },
            painter = painterResource(id = drawable),
            contentDescription = null,
            tint = Colors.lightBlue500,
        )
    }
}

@Composable
fun Details(
    key: String,
    keyStyle: TextStyle? = null,
    value: String,
    valueStyle: TextStyle? = null
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    val copiedActionText = stringResource(R.string.copied_action)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(end = 16.dp),
            text = key,
            color = Colors.darkGray200,
            fontSize = 14.sp,
            style = keyStyle ?: LocalTextStyle.current
        )

        Text(
            modifier = Modifier.clickable {
                clipboardManager.setText(AnnotatedString(value))
                Toast.makeText(
                    context,
                    "$key - $copiedActionText",
                    Toast.LENGTH_SHORT
                ).show()
            },
            text = value,
            color = Colors.darkGray300,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            style = valueStyle ?: LocalTextStyle.current,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Preview
@Composable
private fun Preview() {
    val node = NodeUiModel(
        publicKey = "03864ef025fde8fb587d989186ce6a4a186895ee44a926bfc370e2c366597a3f8f",
        alias = "Kraken \uD83D\uDC19⚡",
        channels = "23620",
        channelsRaw = 23620,
        capacity = "285.48992",
        capacityRaw = 285.48992,
        firstSeen = "03/15/2022 05:39",
        firstSeenRaw = 0,
        updatedAt = "03/15/2022 05:39",
        updatedAtRaw = 0,
        city = "Boardman",
        country = "EUA",
        isFavorite = false,
    )

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        NodeCard(
            node = node
        )

        NodeCard(
            nodeCardResizing = NodeCardResizing.FILL,
            position = 1,
            node = node
        )
    }
}