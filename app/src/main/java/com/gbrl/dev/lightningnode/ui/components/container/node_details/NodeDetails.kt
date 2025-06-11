package com.gbrl.dev.lightningnode.ui.components.container.node_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gbrl.dev.lightningnode.R
import com.gbrl.dev.lightningnode.model.NodeUiModel
import com.gbrl.dev.lightningnode.ui.bottom_sheet.SheetContainer
import com.gbrl.dev.lightningnode.ui.components.container.node_card.Details
import com.gbrl.dev.lightningnode.ui.theme.Colors

@Composable
fun NodeDetails(
    node: NodeUiModel?
) {
    SheetContainer {
        node?.let {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Colors.lightGray200)
                    .padding(bottom = 8.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {

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

                    Details(
                        key = stringResource(R.string.city_title),
                        value = node.city
                    )

                    Details(
                        key = stringResource(R.string.first_seen_title),
                        value = node.firstSeen
                    )

                    Details(
                        key = stringResource(R.string.last_update_title),
                        value = node.updatedAt
                    )

                    Details(
                        key = stringResource(R.string.public_key_title),
                        value = node.publicKey
                    )
                }
            }
        }
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
        isFavorite = false
    )

    NodeDetails(
        node = node
    )
}