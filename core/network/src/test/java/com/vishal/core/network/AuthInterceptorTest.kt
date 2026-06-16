package com.vishal.core.network

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AuthInterceptorTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var okHttpClient: OkHttpClient
    private val testToken = "test_token_123"

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val interceptor = AuthInterceptor(testToken)
        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `AuthInterceptor should add Authorization header with Bearer token`() {
        // Given
        mockWebServer.enqueue(MockResponse().setResponseCode(200))

        // When
        val request = Request.Builder()
            .url(mockWebServer.url("/"))
            .build()
        okHttpClient.newCall(request).execute()

        // Then
        val recordedRequest = mockWebServer.takeRequest()
        assertEquals("Bearer $testToken", recordedRequest.getHeader("Authorization"))
    }
}
