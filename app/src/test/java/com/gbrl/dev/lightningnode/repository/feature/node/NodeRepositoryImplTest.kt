package com.gbrl.dev.lightningnode.repository.feature.node

import com.gbrl.dev.lightningnode.mock.node.NodeMock
import com.gbrl.dev.lightningnode.networking.feature.node.impl.NodeNetworkingImpl
import com.gbrl.dev.lightningnode.networking.model.NetworkingResponse
import com.gbrl.dev.lightningnode.networking.provider.serializer.JsonSerializer
import com.gbrl.dev.lightningnode.networking.provider.serializer.impl.GsonSerializerImpl
import com.gbrl.dev.lightningnode.repository.feature.common.PreferencesRepository
import com.gbrl.dev.lightningnode.repository.feature.node.impl.NodeRepositoryImpl
import com.gbrl.dev.lightningnode.repository.onSuccess
import com.gbrl.dev.lightningnode.storage.feature.node.dao.NodeDao
import com.google.gson.Gson
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class NodeRepositoryImplTest {

    private lateinit var nodeRepository: NodeRepositoryImpl
    private lateinit var nodeNetworking: NodeNetworkingImpl
    private lateinit var preferencesRepository: PreferencesRepository
    private lateinit var nodeDao: NodeDao
    private lateinit var jsonSerializer: JsonSerializer
    private lateinit var gson: Gson

    @Before
    fun setup() {
        gson = Gson()
        jsonSerializer = GsonSerializerImpl(gson)
        nodeNetworking = mockk<NodeNetworkingImpl>()
        nodeDao = mockk()
        preferencesRepository = mockk()

        nodeRepository = NodeRepositoryImpl(
            nodeNetworking = nodeNetworking,
            preferencesRepository = preferencesRepository,
            nodeDao = nodeDao,
            jsonSerializer = jsonSerializer
        )
    }

    @Test
    fun `GIVEN success network result WHEN getNodes is called THEN it should return a list of nodes`() =
        runTest {
            val publicKey = "123"

            val jsonPayload = """
            [
              {
                "publicKey": "$publicKey",
                "alias": "",
                "channels": 1,
                "capacity": 1,
                "firstSeen": 1,
                "updatedAt": 1,
                "city": null,
                "country": {
                  "de": "Brooklyn"
                },
                "iso_code": "US",
                "subdivision": null
              }
            ]
        """.trimIndent()

            val nodeList: List<*>? = jsonSerializer.fromJson(jsonPayload, List::class)
            val mockNetworkResponse = NetworkingResponse(
                data = nodeList,
                httpCode = 200
            )

            coEvery { nodeNetworking.getNodes() } returns mockNetworkResponse
            coEvery { preferencesRepository.getLastFetchTime() } returns 0L
            coEvery { preferencesRepository.setLastFetchTime(any()) } returns Unit
            coEvery { nodeDao.insertAll(any()) } returns Unit
            coEvery { nodeDao.getFavoriteNodes() } returns listOf()

            var givenResult: List<NodeDTO>? = null

            val response = nodeRepository.getNodes(true)

            assertTrue(response.isSuccess)

            response.onSuccess {
                givenResult = it
            }

            assertEquals(publicKey, givenResult?.getOrNull(0)?.publicKey)
        }

    @Test
    fun `GIVEN valid database cached result WHEN getNodes is called THEN it should return a list of nodes`() =
        runTest {
            val nodeList = List(100) { index -> NodeMock.buildNodeEntityModel(publicKey = index.toString()) }
            val databaseResponse = nodeList

            val timer = System.currentTimeMillis() - 5 * 60 * 1000
            coEvery { preferencesRepository.getLastFetchTime() } returns timer
            coEvery { preferencesRepository.setLastFetchTime(any()) } returns Unit

            coEvery { nodeDao.getTopNodesByConnectivity(100) } returns databaseResponse
            coEvery { nodeDao.insertAll(any()) } returns Unit
            coEvery { nodeDao.getFavoriteNodes() } returns listOf()

            var givenResult: List<NodeDTO>? = null

            val response = nodeRepository.getNodes(false)

            response.onSuccess {
                givenResult = it
            }

            assertTrue(response.isSuccess)
            assertEquals(0.toString(), givenResult?.getOrNull(0)?.publicKey)
        }

    @Test
    fun `GIVEN invalid database cached result WHEN getNodes is called THEN it should fetch nodes from remote`() =
        runTest {
            val publicKey = "123"

            val jsonPayload = """
            [
              {
                "publicKey": "$publicKey",
                "alias": "",
                "channels": 1,
                "capacity": 1,
                "firstSeen": 1,
                "updatedAt": 1,
                "city": null,
                "country": {
                  "de": "Brooklyn"
                },
                "iso_code": "US",
                "subdivision": null
              }
            ]
        """.trimIndent()

            val nodeList: List<*>? = jsonSerializer.fromJson(jsonPayload, List::class)
            val mockNetworkResponse = NetworkingResponse(
                data = nodeList,
                httpCode = 200
            )

            val timer = System.currentTimeMillis() - 12 * 60 * 1000
            coEvery { preferencesRepository.getLastFetchTime() } returns timer
            coEvery { preferencesRepository.setLastFetchTime(any()) } returns Unit

            coEvery { nodeNetworking.getNodes() } returns mockNetworkResponse
            coEvery { nodeDao.insertAll(any()) } returns Unit
            coEvery { nodeDao.getFavoriteNodes() } returns listOf()

            var givenResult: List<NodeDTO>? = null

            val response = nodeRepository.getNodes(false)

            response.onSuccess {
                givenResult = it
            }

            coVerify(atLeast = 1) { nodeNetworking.getNodes() }
            coVerify(exactly = 0) { nodeDao.getTopNodesByConnectivity(any()) }

            assertTrue(response.isSuccess)
            assertEquals(publicKey, givenResult?.getOrNull(0)?.publicKey)
        }

    @Test
    fun `GIVEN null network response data WHEN getNodes is called THEN it should return failure result`() =
        runTest {
            val nodeList: List<*>? = null
            val mockNetworkResponse = NetworkingResponse(
                data = nodeList,
                httpCode = 503
            )

            coEvery { nodeNetworking.getNodes() } returns mockNetworkResponse
            coEvery { preferencesRepository.getLastFetchTime() } returns 0L
            coEvery { preferencesRepository.setLastFetchTime(any()) } returns Unit
            coEvery { nodeDao.insertAll(any()) } returns Unit
            coEvery { nodeDao.getFavoriteNodes() } returns listOf()

            val response = nodeRepository.getNodes(true)

            assertTrue(response.isSuccess.not())
            assertTrue(response.toException() is NullPointerException)
        }
}