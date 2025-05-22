package com.nwanvu.txe.data.network

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test

/**
 * Unit tests for the implementation of [Either]
 */
class EitherTest {
    private lateinit var either: Either<Int, String>
    private val successResult = "Success"

    @Test
    fun coFold_returnsRight() {
        runTest {
            either = successResult.toRight()

            val result = either.coFold({ fail("Shouldn't be happened") }, { "data" })

            assertEquals("data", result)
        }
    }

    @Test
    fun coFold_returnsRightInNull() {
        runTest {
            either = successResult.toRight()

            val result = either.coFold({ fail("Shouldn't be happened") }, { null })

            assertEquals(null, result)
        }
    }

    @Test
    fun coFold_returnsLeft() {
        runTest {
            either = 500.toLeft()

            val result = either.coFold({ successResult }, { fail("Shouldn't be happened") })

            assertEquals(successResult, result)
        }
    }

    @Test
    fun map_returnsLeft() {
        either = 404.toLeft()

        val result = either.map { successResult }

        assertTrue(result.isLeft)
        assertEquals(either, result)
    }

    @Test
    fun map_returnsRight() {
        either = successResult.toRight()

        val result = either.map { "data" }

        assertTrue(result.isRight)
        assertEquals("data".toRight(), result)
    }

    @Test
    fun coMap_returnsLeft() {
        runTest {
            either = 404.toLeft()

            val result = either.coMap { successResult }

            assertTrue(result.isLeft)
            assertEquals(either, result)
        }
    }

    @Test
    fun coMap_returnsRight() {
        runTest {
            either = successResult.toRight()

            val result = either.coMap { "data" }

            assertTrue(result.isRight)
            assertEquals("data".toRight(), result)
        }
    }

    @Test
    fun flatMap_returnsLeft() {
        either = 404.toLeft()

        val result = either.flatMap { successResult.toRight() }

        assertTrue(result.isLeft)
        assertEquals(either, result)
    }

    @Test
    fun flatMap_returnsRight() {
        either = successResult.toRight()

        val result = either.flatMap { "data".toRight() }

        assertTrue(result.isRight)
        assertEquals("data".toRight(), result)
    }

    @Test
    fun coFlatMap_returnsLeft() {
        runTest {
            either = 404.toLeft()

            val result = either.coFlatMap { successResult.toRight() }

            assertTrue(result.isLeft)
            assertEquals(either, result)
        }
    }

    @Test
    fun coFlatMap_returnsRight() {
        runTest {
            either = successResult.toRight()

            val result = either.coFlatMap { "data".toRight() }

            assertTrue(result.isRight)
            assertEquals("data".toRight(), result)
        }
    }

    @Test
    fun fold_returnsRight() {
        either = successResult.toRight()

        val result = either.fold({ fail("Shouldn't be happened") }, { 401 })

        assertEquals(401, result)
    }

    @Test
    fun fold_returnsRightInNull() {
        either = successResult.toRight()

        val result = either.fold({ fail("Shouldn't be happened") }, { null })

        assertEquals(null, result)
    }

    @Test
    fun fold_returnsLeft() {
        either = 500.toLeft()

        val result = either.fold({ successResult }, { fail("Shouldn't be happened") })

        assertEquals(successResult, result)
    }

    @Test
    fun getOrElse_ifLeft() {
        either = 404.toLeft()

        val result = either.getOrElse(200)

        assertEquals(200, result)
    }

    @Test
    fun getOrElse_ifRight() {
        either = successResult.toRight()

        val result = either.getOrElse("data")

        assertEquals(successResult, result)
    }
}