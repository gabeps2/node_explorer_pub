package com.gbrl.dev.lightningnode.storage.feature.node.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "node",
    indices = [Index(value = ["public_key"], unique = true)]
)
data class NodeEntityModel(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    @ColumnInfo(name = "public_key")
    val publicKey: String?,
    @ColumnInfo(name = "alias")
    val alias: String?,
    @ColumnInfo(name = "channels")
    val channels: Long?,
    @ColumnInfo(name = "capacity")
    val capacity: Double?,
    @ColumnInfo(name = "firstSeen")
    val firstSeen: Long?,
    @ColumnInfo(name = "updatedAt")
    val updatedAt: Long?,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false,

    val city: Map<String, String>?,
    val country: Map<String, String>?,
)