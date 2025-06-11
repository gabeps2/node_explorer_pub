package com.gbrl.dev.lightningnode.networking.model

data class NetworkingResponse <T>(
    val data: T,
    val httpCode: Int?
)