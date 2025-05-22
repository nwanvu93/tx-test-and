package com.nwanvu.txe.domain.entity

import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Unit tests for the implementation of [ResponseEntity]
 */
class ResponseEntityTest {

    /**
     * Test [ResponseEntity.isSuccess] when response has data and no error
     */
    @Test
    fun hasData_noError() {
        val response = ResponseEntity(null, "data")
        assertTrue(response.isSuccess())
    }

    /**
     * Test [ResponseEntity.isSuccess] when response has data and error
     */
    @Test
    fun hasData_hasError() {
        val response = ResponseEntity("Unknown error", "data")
        assertEquals(false, response.isSuccess())
    }

    /**
     * Test [ResponseEntity.isSuccess] when response has no data and no error
     */
    @Test
    fun noData_noError() {
        val response = ResponseEntity(null, null)
        assertTrue(response.isSuccess())
    }

    /**
     * Test [ResponseEntity.isSuccess] when response has no data and error
     */
    @Test
    fun noData_hasError() {
        val response = ResponseEntity("Unknown error", null)
        assertEquals(false, response.isSuccess())
    }
}