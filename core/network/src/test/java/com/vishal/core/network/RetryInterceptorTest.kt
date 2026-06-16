package com.vishal.core.network

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RetryInterceptorTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var okHttpClient: OkHttpClient

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `RetryInterceptor should retry when response is unsuccessful`() {
        // Given
        val maxRetry = 2
        val interceptor = RetryInterceptor(maxRetry)
        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        // Enqueue 2 failures and then 1 success (Total 3 calls: initial + 2 retries)
        mockWebServer.enqueue(MockResponse().setResponseCode(503))
        mockWebServer.enqueue(MockResponse().setResponseCode(503))
        mockWebServer.enqueue(MockResponse().setResponseCode(200))

        // When
        val request = Request.Builder()
            .url(mockWebServer.url("/"))
            .build()
        val response = okHttpClient.newCall(request).execute()

        // Then
        assertEquals(200, response.code)
        assertEquals(3, mockWebServer.requestCount)
    }

    @Test
    fun `RetryInterceptor should stop after max retries`() {
        // Given
        val maxRetry = 1
        val interceptor = RetryInterceptor(maxRetry)
        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        // Enqueue 3 failures (Total calls should be 2: initial + 1 retry)
        mockWebServer.enqueue(MockResponse().setResponseCode(500))
        mockWebServer.enqueue(MockResponse().setResponseCode(500))
        mockWebServer.enqueue(MockResponse().setResponseCode(500))

        // When
        val request = Request.Builder()
            .url(mockWebServer.url("/"))
            .build()
        val response = okHttpClient.newCall(request).execute()

        // Then
        assertEquals(500, response.code)
        assertEquals(2, mockWebServer.requestCount)
    }
}
