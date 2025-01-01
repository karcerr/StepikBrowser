package com.stepikbrowser.data.stepik.util

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

class AccessTokenInterceptor(private val sharedPreferences: SharedPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = sharedPreferences.getString("access_token", "")
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(request)
    }
}