package com.gbrl.dev.lightningnode.repository

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ResultTest {
    @Test
    fun `GIVEN success result WHEN onSuccess is called THEN it should call block`() {
        val expectResult = 10

        val operation: () -> Int = { 5 + 5 }

        val result = result { operation() }

        var givenResult: Int? = null

        result.onSuccess {
            givenResult = it
        }

        assertEquals(expectResult, givenResult)
    }

    @Test
    fun `GIVEN success result THEN isSuccess variable should be true`() {
        val operation: () -> Int = { 5 + 5 }

        val result = result { operation() }

        assertTrue(result.isSuccess)
    }

    @Test(expected = ArithmeticException::class)
    fun `GIVEN exception result WHEN onFailure is called THEN it should call block`() {
        val operation: () -> Int = { 5 / 0 }

        val result = result { operation() }

        var givenResult: Throwable? = null

        result.onFailure {
            givenResult = it
        }

        givenResult?.let { throw it }
    }

    @Test
    fun `GIVEN exception result THEN isSuccess should be false`() {
        val operation: () -> Int = { 5 / 0 }

        val result = result { operation() }

        assertFalse(result.isSuccess)
    }
}