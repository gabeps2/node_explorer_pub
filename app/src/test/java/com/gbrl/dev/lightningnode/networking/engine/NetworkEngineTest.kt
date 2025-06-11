package com.gbrl.dev.lightningnode.networking.engine

import com.gbrl.dev.lightningnode.networking.provider.retrofit.RetrofitService
import com.gbrl.dev.lightningnode.networking.provider.serializer.impl.GsonSerializerImpl
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import retrofit2.HttpException
import retrofit2.Retrofit
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class NetworkEngineTest {
    private lateinit var retrofit: Retrofit

    private lateinit var gson: Gson
    private lateinit var gsonSerializer: GsonSerializerImpl

    private lateinit var mockWebServer: MockWebServer
    private lateinit var networkEngine: NetworkEngine

    @Before
    fun setup() {
        gson = Gson()
        gsonSerializer = GsonSerializerImpl(gson)

        mockWebServer = MockWebServer()
        mockWebServer.start()

        retrofit = Retrofit
            .Builder()
            .baseUrl(mockWebServer.url("/"))
            .build()

        val retrofitService = retrofit.create(RetrofitService::class.java)

        networkEngine = NetworkEngine(retrofitService, gsonSerializer)
    }

    @Test
    fun `GIVEN success API response WHEN get request is called THEN it should return correct response`() =
        runTest {
            val name = "John Doe"
            val age = 39

            val jsonResponse = """
            {
                "name": "$name",
                "age": $age
            }
        """.trimIndent()

            val expectedHttpCode = 200

            mockWebServer.enqueue(
                MockResponse().setBody(jsonResponse).setResponseCode(expectedHttpCode)
            )

            val networkResponse = networkEngine.getRequest("", mapOf(), User::class)

            assertTrue(networkResponse.httpCode == expectedHttpCode)
            assertNotNull(networkResponse.data)
            assertEquals(name, networkResponse.data.name)
            assertEquals(age, networkResponse.data.age)
        }

    @Test(expected = HttpException::class)
    fun `GIVEN failure API response WHEN get request is called THEN it should return throw HttpException `() = runTest {
        val name = "John Doe"
        val age = 39

        val jsonResponse = """
            {
                "name": "$name",
                "age": $age
            }
        """.trimIndent()

        val expectedHttpCode = 422

        mockWebServer.enqueue(
            MockResponse().setBody(jsonResponse).setResponseCode(expectedHttpCode)
        )

        networkEngine.getRequest("", mapOf(), User::class)
    }

    private data class User(
        @SerializedName("name")
        val name: String,

        @SerializedName("age")
        val age: Int
    )
}