package com.gbrl.dev.lightningnode.storage.feature.node.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gbrl.dev.lightningnode.storage.feature.node.entity.NodeEntityModel

@Dao
interface NodeDao {
    @Query("SELECT * FROM node ORDER BY channels DESC LIMIT (:limit)")
    suspend fun getTopNodesByConnectivity(limit: Int): List<NodeEntityModel>

    @Query("UPDATE node SET is_favorite = :isFavorite WHERE public_key = :publicKey")
    suspend fun updateFavorite(isFavorite: Boolean, publicKey: String)

    @Query("SELECT * FROM node WHERE is_favorite = 1")
    suspend fun getFavoriteNodes(): List<NodeEntityModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<NodeEntityModel>)
}