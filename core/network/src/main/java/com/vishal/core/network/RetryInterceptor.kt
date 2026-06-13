package com.vishal.core.network

import okhttp3.Interceptor
import okhttp3.Response

class RetryInterceptor(private val maxRetry: Int = 3):Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(request)
        var tryCount = 0

        // Retry if the server request fails or returns a specific server error (e.g., 503)
        while (!response.isSuccessful && tryCount < maxRetry) {
            tryCount++
            // Close the previous response body to avoid resource leaks
            response.close()
            // Retry the same request
            response = chain.proceed(request)
        }

        return response
    }
}