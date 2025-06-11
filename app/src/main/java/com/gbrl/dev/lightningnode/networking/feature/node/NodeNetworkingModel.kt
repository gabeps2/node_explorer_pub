package com.gbrl.dev.lightningnode.networking.feature.node

import com.google.gson.annotations.SerializedName

data class NodeNetworkingModel(
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
)