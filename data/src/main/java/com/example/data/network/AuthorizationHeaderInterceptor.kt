package com.example.data.network

import okhttp3.Interceptor
import okhttp3.Response


class AuthorizationHeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val builder = originalRequest.newBuilder().header(
            "Authorization",
            TOKEN
        )
        val newRequest = builder.build()
        return chain.proceed(newRequest)
    }

    companion object {
        private const val TOKEN = "732d7b35-0b0c-496f-88fc-af4c8e7fbe50"
    }
}