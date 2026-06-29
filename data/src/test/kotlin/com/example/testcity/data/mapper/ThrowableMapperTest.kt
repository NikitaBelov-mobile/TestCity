package com.example.testcity.data.mapper

import com.example.testcity.domain.model.AppError
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.IOException
import java.net.SocketTimeoutException

class ThrowableMapperTest {

    private val mapper = ThrowableMapper()

    @Test
    fun `maps IOException to NoNetwork`() {
        assertEquals(AppError.NoNetwork, mapper.map(IOException("offline")))
    }

    @Test
    fun `maps socket timeout to Timeout`() {
        assertEquals(AppError.Timeout, mapper.map(SocketTimeoutException("timeout")))
    }

    @Test
    fun `maps unknown to Unknown`() {
        assertEquals(AppError.Unknown("boom"), mapper.map(IllegalStateException("boom")))
    }
}
