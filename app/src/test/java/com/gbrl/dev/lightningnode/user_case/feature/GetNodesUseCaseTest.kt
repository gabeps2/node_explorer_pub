package com.gbrl.dev.lightningnode.user_case.feature

import com.gbrl.dev.lightningnode.mock.node.NodeMock
import com.gbrl.dev.lightningnode.model.NodeUiModel
import com.gbrl.dev.lightningnode.repository.Result
import com.gbrl.dev.lightningnode.repository.feature.node.NodeDTO
import com.gbrl.dev.lightningnode.repository.feature.node.impl.NodeRepositoryImpl
import com.gbrl.dev.lightningnode.repository.result
import com.gbrl.dev.lightningnode.usecase.feature.node.GetNodesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class GetNodesUseCaseTest {
    private lateinit var nodeRepository: NodeRepositoryImpl
    private lateinit var getNodesUseCase: GetNodesUseCase

    @Before
    fun setup() {
        nodeRepository = mockk<NodeRepositoryImpl>()
        getNodesUseCase = GetNodesUseCase(nodeRepository)
    }

    @Test
    fun `GIVEN success result from repository WHEN getNodesUseCase is performed THEN it should return the expected result`() =
        runTest {
            val nodeList = result { listOf(NodeMock.buildNodeDTO()) }

            coEvery { nodeRepository.getNodes(false) } returns nodeList

            val result = getNodesUseCase.invoke(false)

            assertTrue { result.isSuccess }
            assertNotNull(result.toValue())
            assertTrue { result.toValue()?.isNotEmpty() == true }
            assertTrue { result.toValue()?.first() is NodeUiModel }
        }

    @Test
    fun `GIVEN failure result from repository WHEN getNodesUseCase is performed THEN it should throw the result exception`() =
        runTest {
            val exceptionMessage = "Test exception"
            val exception = Throwable(exceptionMessage)

            val nodeNetworkingModelList = Result<List<NodeDTO>>(exception)

            coEvery { nodeRepository.getNodes(false) } returns nodeNetworkingModelList

            val result = getNodesUseCase.invoke(false)

            assertTrue { result.isSuccess.not() }
            assertNotNull(result.toException())
            assertEquals(exceptionMessage, result.toException()?.message)
        }

    @Test
    fun `GIVEN a timestamp WHEN getNodesUseCase is performed THEN it should be parsed to US date`() =
        runTest {
            val expectedDate = "01/02/2020 20:00"
            val datePattern = "MM/dd/yyyy HH:mm"
            val toMilliseconds = 1000

            val timestampAsMilliseconds = SimpleDateFormat(datePattern, Locale.US).parse(expectedDate)!!.time
            val timestampAsSeconds = timestampAsMilliseconds / toMilliseconds

            val node = NodeMock.buildNodeDTO(updatedAt = timestampAsSeconds)
            val nodeList = result { listOf(node) }

            coEvery { nodeRepository.getNodes(false) } returns nodeList

            val result = getNodesUseCase.invoke(false).toValue()!!

            assertEquals(expectedDate, result.first().updatedAt)
        }

    @Test
    fun `GIVEN a null timestamp WHEN getNodesUseCase is performed THEN the value returned should be a empty string`() =
        runTest {
            val node = NodeMock.buildNodeDTO()
            val nodeList = result { listOf(node.copy(updatedAt = null)) }

            coEvery { nodeRepository.getNodes(false) } returns nodeList

            val result = getNodesUseCase.invoke(false).toValue()!!

            assertTrue(result.first().updatedAt.isEmpty())
        }

    @Test
    fun `GIVEN satoshi capacity WHEN getNodesUseCase is performed THEN it should be parsed to BTC format`() =
        runTest {
            val satoshis = 13000492.0
            val satsVsBtc = 100_000_000.0

            val expectedValue = (satoshis / satsVsBtc).toString()

            val node = NodeMock.buildNodeDTO(capacity = satoshis)
            val nodeList = result { listOf(node) }

            coEvery { nodeRepository.getNodes(false) } returns nodeList

            val result = getNodesUseCase.invoke(false).toValue()!!

            assertEquals(expectedValue, result.first().capacity)
        }

    @Test
    fun `GIVEN null satoshi capacity WHEN getNodesUseCase is performed THEN it should return a empty string`() =
        runTest {
            val node = NodeMock.buildNodeDTO()
            val nodeList = result { listOf(node.copy(capacity = null)) }

            coEvery { nodeRepository.getNodes(false) } returns nodeList

            val result = getNodesUseCase.invoke(false).toValue()!!

            assertTrue(result.first().capacity.isEmpty())
        }

    @Test
    fun `GIVEN a country map with pt-BR available WHEN getNodesUseCase is performed THEN it should return country name in pt-BR`() =
        runTest {
            val country = "EUA"

            val node = NodeMock.buildNodeDTO(
                country = mapOf(
                    "pt-BR" to country
                )
            )
            val nodeList = result { listOf(node) }

            coEvery { nodeRepository.getNodes(false) } returns nodeList

            val result = getNodesUseCase.invoke(false).toValue()!!

            assertEquals(country, result.first().country)
        }

    @Test
    fun `GIVEN a country map without pt-BR and en available WHEN getNodesUseCase is performed THEN it should return country name in en`() =
        runTest {
            val country = "EUA"

            val node = NodeMock.buildNodeDTO(
                country = mapOf(
                    "en" to country
                )
            )
            val nodeList = result { listOf(node) }

            coEvery { nodeRepository.getNodes(false) } returns nodeList

            val result = getNodesUseCase.invoke(false).toValue()!!

            assertEquals(country, result.first().country)
        }

    @Test
    fun `GIVEN a country map with pt-BR and en available WHEN getNodesUseCase is performed THEN it should return country name in pt-BR`() =
        runTest {
            val countryPtBr = "EUA"
            val countryEn = "USA"

            val node = NodeMock.buildNodeDTO(
                country = mapOf(
                    "pt-BR" to countryPtBr,
                    "en" to countryEn
                )
            )
            val nodeList = result { listOf(node) }

            coEvery { nodeRepository.getNodes(false) } returns nodeList

            val result = getNodesUseCase.invoke(false).toValue()!!

            assertEquals(countryPtBr, result.first().country)
        }

    @Test
    fun `GIVEN a country map without pt-BR and en WHEN getNodesUseCase is performed THEN it should return a empty string`() =
        runTest {
            val country = "EUA"

            val node = NodeMock.buildNodeDTO(
                country = mapOf(
                    "fr" to country
                )
            )
            val nodeList = result { listOf(node) }

            coEvery { nodeRepository.getNodes(false) } returns nodeList

            val result = getNodesUseCase.invoke(false).toValue()!!

            assertTrue(result.first().country.isEmpty())
        }

    @Test
    fun `GIVEN a city map with pt-BR available WHEN getNodesUseCase is performed THEN it should return country name in pt-BR`() =
        runTest {
            val city = "Zurique"

            val node = NodeMock.buildNodeDTO(
                city = mapOf(
                    "pt-BR" to city
                )
            )
            val nodeList = result { listOf(node) }

            coEvery { nodeRepository.getNodes(false) } returns nodeList

            val result = getNodesUseCase.invoke(false).toValue()!!

            assertEquals(city, result.first().city)
        }

    @Test
    fun `GIVEN a city map without pt-BR and en available WHEN getNodesUseCase is performed THEN it should return country name in en`() =
        runTest {
            val city = "Zürich"

            val node = NodeMock.buildNodeDTO(
                city = mapOf(
                    "en" to city
                )
            )
            val nodeList = result { listOf(node) }

            coEvery { nodeRepository.getNodes(false) } returns nodeList

            val result = getNodesUseCase.invoke(false).toValue()!!

            assertEquals(city, result.first().city)
        }

    @Test
    fun `GIVEN a city map with pt-BR and en available WHEN getNodesUseCase is performed THEN it should return country name in pt-BR`() =
        runTest {
            val cityPtBr = "Zurique"
            val cityEn = "Zürich"

            val node = NodeMock.buildNodeDTO(
                country = mapOf(
                    "pt-BR" to cityPtBr,
                    "en" to cityEn
                )
            )
            val nodeList = result { listOf(node) }

            coEvery { nodeRepository.getNodes(false) } returns nodeList

            val result = getNodesUseCase.invoke(false).toValue()!!

            assertEquals(cityPtBr, result.first().country)
        }

    @Test
    fun `GIVEN a city map without pt-BR and en WHEN getNodesUseCase is performed THEN it should return a empty string`() =
        runTest {
            val country = "Zürich"

            val node = NodeMock.buildNodeDTO(
                country = mapOf(
                    "fr" to country
                )
            )
            val nodeList = result { listOf(node) }

            coEvery { nodeRepository.getNodes(false) } returns nodeList

            val result = getNodesUseCase.invoke(false).toValue()!!

            assertTrue(result.first().country.isEmpty())
        }
}