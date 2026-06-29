package com.example.testcity.feature.cities.mapper

import com.example.testcity.core.ui.R
import com.example.testcity.core.ui.mapper.ErrorUiMapper
import com.example.testcity.core.ui.model.UiText
import com.example.testcity.domain.model.AppError
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

class ErrorUiMapperTest {

    @Test
    fun `maps no network as retryable`() {
        val error = ErrorUiMapper.fromAppError(AppError.NoNetwork)
        assertEquals(UiText.Resource(R.string.error_no_network), error.message)
        assertTrue(error.isRetryable)
    }

    @Test
    fun `maps server 500 as retryable`() {
        val error = ErrorUiMapper.fromAppError(AppError.Server(500))
        assertTrue(error.isRetryable)
    }

    @Test
    fun `maps server 404 as non retryable with client error resource`() {
        val error = ErrorUiMapper.fromAppError(AppError.Server(404))
        assertFalse(error.isRetryable)
        assertEquals(UiText.ResourceInt(R.string.error_client_format, 404), error.message)
    }

    @Test
    fun `maps IOException to no network`() {
        val error = ErrorUiMapper.fromThrowable(IOException("offline"))
        assertEquals(UiText.Resource(R.string.error_no_network), error.message)
    }

    @Test
    fun `maps city not found to resource`() {
        val error = ErrorUiMapper.fromAppError(AppError.CityNotFound)
        assertEquals(UiText.Resource(R.string.city_not_found), error.message)
    }
}
