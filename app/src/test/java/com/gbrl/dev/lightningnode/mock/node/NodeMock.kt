package com.gbrl.dev.lightningnode.mock.node

import com.gbrl.dev.lightningnode.networking.feature.node.NodeNetworkingModel
import com.gbrl.dev.lightningnode.repository.feature.node.NodeDTO
import com.gbrl.dev.lightningnode.storage.feature.node.entity.NodeEntityModel

class NodeMock {
    companion object {
        fun buildNodeNetworkingModel(
            publicKey: String? = null,
            alias: String? = null,
            channels: Long? = null,
            capacity: Double? = null,
            firstSeen: Long? = null,
            updatedAt: Long? = null,
            city: Map<String, String>? = null,
            country: Map<String, String>? = null
        ): NodeNetworkingModel =
            NodeNetworkingModel(
                publicKey = publicKey ?: "123",
                alias = alias ?: "alias",
                channels = channels ?: 2908,
                capacity = capacity ?: 36010516297.0,
                firstSeen = firstSeen ?: 1522941222,
                updatedAt = updatedAt ?: 1661274935,
                city = city ?: mapOf(
                    "pt-BR" to "Vancôver",
                    "en" to "Vancouver",
                    "fr" to "Vancouver"
                ),
                country = country ?: mapOf(
                    "pt-BR" to "EUA",
                    "en" to "United States",
                    "fr" to "États Unis"
                ),
            )

        fun buildNodeDTO(
            publicKey: String? = null,
            alias: String? = null,
            channels: Long? = null,
            capacity: Double? = null,
            firstSeen: Long? = null,
            updatedAt: Long? = null,
            city: Map<String, String>? = null,
            country: Map<String, String>? = null,
            isFavorite: Boolean = false
        ): NodeDTO =
            NodeDTO(
                publicKey = publicKey ?: "123",
                alias = alias ?: "alias",
                channels = channels ?: 2908,
                capacity = capacity ?: 36010516297.0,
                firstSeen = firstSeen ?: 1522941222,
                updatedAt = updatedAt ?: 1661274935,
                city = city ?: mapOf(
                    "pt-BR" to "Vancôver",
                    "en" to "Vancouver",
                    "fr" to "Vancouver"
                ),
                country = country ?: mapOf(
                    "pt-BR" to "EUA",
                    "en" to "United States",
                    "fr" to "États Unis"
                ),
                isFavorite = isFavorite
            )

        fun buildNodeEntityModel(
            publicKey: String? = null,
            alias: String? = null,
            channels: Long? = null,
            capacity: Double? = null,
            firstSeen: Long? = null,
            updatedAt: Long? = null,
            city: Map<String, String>? = null,
            country: Map<String, String>? = null,
            isFavorite: Boolean = false
        ): NodeEntityModel =
            NodeEntityModel(
                publicKey = publicKey ?: "123",
                alias = alias ?: "alias",
                channels = channels ?: 2908,
                capacity = capacity ?: 36010516297.0,
                firstSeen = firstSeen ?: 1522941222,
                updatedAt = updatedAt ?: 1661274935,
                city = city ?: mapOf(
                    "pt-BR" to "Vancôver",
                    "en" to "Vancouver",
                    "fr" to "Vancouver"
                ),
                country = country ?: mapOf(
                    "pt-BR" to "EUA",
                    "en" to "United States",
                    "fr" to "États Unis"
                ),
                isFavorite = isFavorite
            )
    }
}