package com.gbrl.dev.lightningnode.networking.provider.serializer.impl

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.junit.Before
import org.junit.Test
import java.io.StringReader
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GsonSerializerImplTest {
    private lateinit var gson: Gson
    private lateinit var gsonSerializer: GsonSerializerImpl

    @Before
    fun setup() {
        gson = Gson()
        gsonSerializer = GsonSerializerImpl(gson)
    }

    @Test
    fun `GIVEN valid json payload WHEN fromJson is called THEN it should deserialize successfully to expected class`() {
        val name = "John Doe"
        val age = 39

        val jsonPayload = """
            {
                "name": "$name",
                "age": $age
            }
        """.trimIndent()

        val result = gsonSerializer.fromJson(jsonPayload, User::class)

        assertEquals(name, result?.name)
        assertEquals(age, result?.age)
    }

    @Test
    fun `GIVEN invalid json payload for the expected class WHEN fromJson is called THEN it should return null`() {
        val jsonPayload = """
            [
                {
                    "test": "test",
                }
            ]
        """.trimIndent()

        val result = gsonSerializer.fromJson(jsonPayload, User::class)

        assertNull(result)
    }

    @Test
    fun `GIVEN valid reader json payload WHEN fromJson is called THEN it should deserialize successfully to expected class`() {
        val name = "John Doe"
        val age = 39

        val jsonPayload = """
            {
                "name": "$name",
                "age": $age
            }
        """.trimIndent()

        val reader = StringReader(jsonPayload)

        val result = gsonSerializer.fromJson(reader, User::class)

        assertEquals(name, result?.name)
        assertEquals(age, result?.age)
    }

    @Test
    fun `GIVEN invalid json payload as reader for the expected class WHEN fromJson is called THEN it should return null`() {
        val jsonPayload = """
            [
                {
                    "test": "test",
                }
            ]
        """.trimIndent()

        val reader = StringReader(jsonPayload)

        val result = gsonSerializer.fromJson(reader, User::class)

        assertNull(result)
    }

    @Test
    fun `GIVEN valid object WHEN toJson is called THEN it should serialize the object to string`() {
        val name = "John Doe"
        val age = 39

        val user = User(name, age)

        val jsonPayload = gson.toJson(user)

        val result = gsonSerializer.toJson(user)

        assertEquals(jsonPayload, result)
    }

    @Test
    fun `GIVEN null object WHEN toJson is called THEN it should return null`() {
        val user: User? = null

        val result = gsonSerializer.toJson(user)

        assertNull(result)
    }

    private data class User(
        @SerializedName("name")
        val name: String,

        @SerializedName("age")
        val age: Int
    )
}

