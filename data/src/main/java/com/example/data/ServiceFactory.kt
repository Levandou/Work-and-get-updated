package com.example.data

import android.os.Build
import com.example.domain.Environment
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

abstract class ServiceFactory(
    private val converterFactory: Converter.Factory,
    private val callAdapterFactory: CallAdapter.Factory,
    private val logLevel: HttpLoggingInterceptor.Level,
    private val environment: Environment
) {

    fun <T> create(serviceType: Class<T>): T {
        return create(
            serviceType,
            getHttpClient(),
            converterFactory,
            callAdapterFactory,
            environment.baseUrl
        )
    }

    private fun getNetAdapter(
        client: OkHttpClient,
        converterFactory: Converter.Factory,
        callAdapterFactory: CallAdapter.Factory,
        baseUrl: String
    ) = Retrofit.Builder()
        .client(client)
        .addConverterFactory(converterFactory)
        .addCallAdapterFactory(callAdapterFactory)
        .baseUrl(baseUrl)
        .build()

    private fun <T> create(
        serviceType: Class<T>,
        client: OkHttpClient,
        converterFactory: Converter.Factory,
        callAdapterFactory: CallAdapter.Factory,
        baseUrl: String
    ): T {
        val retrofit = getNetAdapter(client, converterFactory, callAdapterFactory, baseUrl)
        return retrofit.create(serviceType)
    }

    private fun getHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        interceptors().forEach { builder.addInterceptor(it) }
        builder.addInterceptor(HttpLoggingInterceptor().apply {
            level = logLevel
        }).connectTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
        return builder.build()
    }

    abstract fun interceptors(): List<Interceptor>
}