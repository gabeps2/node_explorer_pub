package com.gbrl.dev.lightningnode.di

import com.gbrl.dev.lightningnode.networking.adapter.NetworkAdapter
import com.gbrl.dev.lightningnode.networking.adapter.impl.NetworkAdapterImpl
import com.gbrl.dev.lightningnode.networking.engine.NetworkEngine
import com.gbrl.dev.lightningnode.networking.feature.node.NodeNetworking
import com.gbrl.dev.lightningnode.networking.feature.node.impl.NodeNetworkingImpl
import com.gbrl.dev.lightningnode.networking.provider.retrofit.RetrofitService
import com.gbrl.dev.lightningnode.networking.provider.retrofit.buildRetrofit
import com.gbrl.dev.lightningnode.networking.provider.serializer.JsonSerializer
import com.gbrl.dev.lightningnode.networking.provider.serializer.impl.GsonSerializerImpl
import com.gbrl.dev.lightningnode.repository.feature.common.PreferencesRepository
import com.gbrl.dev.lightningnode.repository.feature.common.impl.PreferencesRepositoryImpl
import com.gbrl.dev.lightningnode.repository.feature.node.NodeRepository
import com.gbrl.dev.lightningnode.repository.feature.node.impl.NodeRepositoryImpl
import com.gbrl.dev.lightningnode.storage.feature.node.dao.NodeDao
import com.gbrl.dev.lightningnode.storage.feature.node.database.NodeDatabase
import com.gbrl.dev.lightningnode.storage.feature.node.database.buildNodeDatabase
import com.gbrl.dev.lightningnode.ui.screen.home.HomeViewModel
import com.gbrl.dev.lightningnode.ui.screen.node_list.NodeListViewModel
import com.gbrl.dev.lightningnode.usecase.feature.node.GetFavoriteNodeUseCase
import com.gbrl.dev.lightningnode.usecase.feature.node.GetNodesUseCase
import com.gbrl.dev.lightningnode.usecase.feature.node.UpdateFavoriteUseCase
import com.google.gson.Gson
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

object Modules {
    private val networkingModule: Module = module {
        single<Retrofit> {
            buildRetrofit(
                "https://mempool.space/api/v1/"
            )
        }
        single<Gson> { Gson() }
        single<JsonSerializer> { GsonSerializerImpl(get()) }
        single<RetrofitService> { get<Retrofit>().create(RetrofitService::class.java) }
        single<NetworkEngine> { NetworkEngine(get(), get()) }
        single<NetworkAdapter> { NetworkAdapterImpl(get()) }
    }

    private val featureModule: Module = module {
        factory<HomeViewModel> { HomeViewModel(get(), get(), get()) }
        factory<NodeListViewModel> { NodeListViewModel(get(), get()) }

        single<NodeRepository> { NodeRepositoryImpl(get(), get(), get(), get()) }
        factory<NodeNetworking> { NodeNetworkingImpl(get()) }
        factory<UpdateFavoriteUseCase> { UpdateFavoriteUseCase(get()) }
        factory<GetNodesUseCase> { GetNodesUseCase(get()) }
        factory<GetFavoriteNodeUseCase> { GetFavoriteNodeUseCase(get()) }
    }

    private val persistenceModule: Module = module {
        single<NodeDatabase> { buildNodeDatabase(get()) }
        single<NodeDao> { get<NodeDatabase>().nodeDao() }
        single<PreferencesRepository> { PreferencesRepositoryImpl(get()) }
    }

    val modules = listOf<Module>(
        networkingModule,
        persistenceModule,
        featureModule
    )
}