package com.example.testcity.feature.citydetail.mapper

import org.junit.Assert.assertEquals
import org.junit.Test

class BrowserUrlBuilderTest {

    @Test
    fun `builds google search url with encoded query`() {
        val url = BrowserUrlBuilder.googleSearchUrl("New York")
        assertEquals("https://www.google.com/search?q=New+York", url)
    }
}
