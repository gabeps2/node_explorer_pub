package com.gbrl.dev.lightningnode.model

data class NodeUiModel(
    val publicKey: String,
    val alias: String,
    val channels: String,
    val channelsRaw: Long,
    val capacity: String,
    val capacityRaw: Double,
    val firstSeen: String,
    val firstSeenRaw: Long,
    val updatedAt: String,
    val updatedAtRaw: Long,
    val city: String,
    val country: String,
    val isFavorite: Boolean,
)


