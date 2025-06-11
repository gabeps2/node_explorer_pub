package com.gbrl.dev.lightningnode.usecase.feature.node.common

import com.gbrl.dev.lightningnode.model.NodeUiModel
import com.gbrl.dev.lightningnode.repository.feature.node.NodeDTO
import com.gbrl.dev.lightningnode.usecase.feature.node.GetNodesUseCase.Companion.BTC_VS_SATS
import com.gbrl.dev.lightningnode.usecase.feature.node.GetNodesUseCase.Companion.DATE_PATTERN
import com.gbrl.dev.lightningnode.usecase.feature.node.GetNodesUseCase.Companion.EN_US
import com.gbrl.dev.lightningnode.usecase.feature.node.GetNodesUseCase.Companion.PT_BR
import com.gbrl.dev.lightningnode.usecase.feature.node.GetNodesUseCase.Companion.TO_MILLISECONDS
import java.text.SimpleDateFormat
import java.util.Locale

fun NodeDTO.toUiModel(): NodeUiModel {
    return NodeUiModel(
        publicKey = publicKey.orEmpty(),
        alias = alias.orEmpty(),
        channels = channels?.toString() ?: "",
        channelsRaw = channels ?: 0,
        capacity = resolveCapacity(capacity),
        capacityRaw = capacity ?: 0.0,
        firstSeen = resolveDate(firstSeen),
        firstSeenRaw = firstSeen ?: 0,
        updatedAt = resolveDate(updatedAt),
        updatedAtRaw = updatedAt ?: 0,
        city = resolveCity(city),
        country = resolveCountry(country),
        isFavorite = isFavorite == true
    )
}

private fun resolveDate(timestamp: Long?): String {
    return timestamp?.let {
        SimpleDateFormat(DATE_PATTERN, Locale.US).format(it * TO_MILLISECONDS)
    } ?: ""
}

private fun resolveCapacity(sats: Double?): String {
    return sats?.let {
        (sats / BTC_VS_SATS).toString()
    } ?: ""
}

private fun resolveCountry(countries: Map<String, String>?): String {
    var country: String? = null

    countries?.let {
        country = it.getOrElse(PT_BR) {
            it.getOrDefault(EN_US, null)
        }
    }

    return country.orEmpty()
}

private fun resolveCity(cities: Map<String, String>?): String {
    var country: String? = null

    cities?.let {
        country = it.getOrElse(PT_BR) {
            it.getOrDefault(EN_US, null)
        }
    }

    return country.orEmpty()
}