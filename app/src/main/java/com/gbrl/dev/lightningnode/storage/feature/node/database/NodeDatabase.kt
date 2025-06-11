package com.gbrl.dev.lightningnode.storage.feature.node.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gbrl.dev.lightningnode.storage.feature.common.Converters
import com.gbrl.dev.lightningnode.storage.feature.node.dao.NodeDao
import com.gbrl.dev.lightningnode.storage.feature.node.entity.NodeEntityModel

@Database(entities = [NodeEntityModel::class], version = 5)
@TypeConverters(Converters::class)
abstract class NodeDatabase : RoomDatabase() {
    abstract fun nodeDao(): NodeDao
}

fun buildNodeDatabase(context: Context): NodeDatabase =
    Room.databaseBuilder(
        context = context,
        klass = NodeDatabase::class.java,
        name = "node-database"
    ).fallbackToDestructiveMigration(true).build()