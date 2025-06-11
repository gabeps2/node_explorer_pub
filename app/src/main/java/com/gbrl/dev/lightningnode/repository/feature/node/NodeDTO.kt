package com.gbrl.dev.lightningnode.repository.feature.node

import com.gbrl.dev.lightningnode.storage.feature.node.entity.NodeEntityModel
import com.google.gson.annotations.SerializedName

data class NodeDTO(
    @SerializedName("publicKey")
    val publicKey: String?,
    @SerializedName("alias")
    val alias: String?,
    @SerializedName("channels")
    val channels: Long?,
    @SerializedName("capacity")
    val capacity: Double?,
    @SerializedName("firstSeen")
    val firstSeen: Long?,
    @SerializedName("updatedAt")
    val updatedAt: Long?,
    @SerializedName("city")
    val city: Map<String, String>?,
    @SerializedName("country")
    val country: Map<String, String>?,
    @SerializedName("isFavorite")
    val isFavorite: Boolean = false
)

internal fun NodeDTO.toEntity(): NodeEntityModel =
    NodeEntityModel(
        publicKey = this.publicKey,
        alias = this.alias,
        channels = this.channels,
        capacity = this.capacity,
        firstSeen = this.firstSeen,
        updatedAt = this.updatedAt,
        city = this.city,
        country = this.country,
        isFavorite = this.isFavorite
    )

internal fun NodeEntityModel.toNodeDTO(): NodeDTO =
    NodeDTO(
        publicKey = this.publicKey,
        alias = this.alias,
        channels = this.channels,
        capacity = this.capacity,
        firstSeen = this.firstSeen,
        updatedAt = this.updatedAt,
        city = this.city,
        country = this.country,
        isFavorite = this.isFavorite
    )