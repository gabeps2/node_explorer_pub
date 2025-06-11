package com.gbrl.dev.lightningnode

import android.app.Application
import com.gbrl.dev.lightningnode.di.Modules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class LightningNode : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@LightningNode)
            modules(Modules.modules)
        }
    }
}