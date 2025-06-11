package com.gbrl.dev.lightningnode.ui.screen.node_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gbrl.dev.lightningnode.R
import com.gbrl.dev.lightningnode.ui.bottom_sheet.SheetContainer
import com.gbrl.dev.lightningnode.ui.components.core.button.Button
import com.gbrl.dev.lightningnode.ui.components.core.button.ButtonType
import com.gbrl.dev.lightningnode.ui.components.core.radio.Radio
import com.gbrl.dev.lightningnode.ui.components.core.radio.RadioType

@Composable
fun SortBottomSheet(
    currentSortMethod: SortMethod,
    currentOrderMethod: OrderMethod,
    selectedMethod: (SortMethod, OrderMethod) -> Unit
) {
    var selectedMethod by remember { mutableStateOf(currentSortMethod) }
    var orderByMethod by remember { mutableStateOf(currentOrderMethod) }

    SheetContainer {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Sort by",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                )

                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            orderByMethod = when (orderByMethod) {
                                OrderMethod.ASC -> OrderMethod.DESC
                                OrderMethod.DESC -> OrderMethod.ASC
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (orderByMethod == OrderMethod.ASC) R.drawable.ic_sort_bottom_to_top
                            else R.drawable.ic_sort_top_to_bottom
                        ),
                        contentDescription = null,
                    )
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SortMethod.entries.forEach {
                    Radio(
                        selected = it == selectedMethod,
                        label = stringResource(it.toStringId()),
                        type = RadioType.SECONDARY
                    ) {
                        selectedMethod = it
                    }
                }
            }

            Button(
                modifier = Modifier.padding(top = 16.dp),
                label = stringResource(R.string.sort_by_apply_button),
                type = ButtonType.PRIMARY
            ) {
                selectedMethod(selectedMethod, orderByMethod)
            }
        }
    }
}

private fun SortMethod.toStringId(): Int =
    when (this) {
        SortMethod.DEFAULT -> R.string.sort_by_default
        SortMethod.CHANNELS -> R.string.sort_by_channels
        SortMethod.CAPACITY -> R.string.sort_by_capacity
        SortMethod.FIRST_SEEN -> R.string.sort_by_first_seen
        SortMethod.UPDATED_AT -> R.string.sort_by_last_update
    }