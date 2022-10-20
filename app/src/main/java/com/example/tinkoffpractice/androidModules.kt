package com.example.tinkoffpractice

import com.example.data.ApiService
import com.example.data.RepositoryImpl
import com.example.data.db.AppDatabase
import com.example.data.okHttp.NoConnectionPendingException
import com.example.data.paging.PagingSourceNews
import com.example.domain.NewsRepository
import com.example.domain.mappers.NewsMapper
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.coroutineContext

val mainModule = module {
    viewModel { MainViewModel(get()) }
}

val dataModule = module {
    factory<NewsRepository> { RepositoryImpl(get(), get(), get()) }

    single {
        HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        GsonBuilder()
            .setLenient()
            .create()
    }

    single {
        Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setLenient()
                        .create()
                )
            )
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl("http://217.114.156.125:5000/api/")
            .client(OkHttpClient.Builder().addInterceptor(NoConnectionPendingException(androidContext()))/*.addInterceptor(HttpLoggingInterceptor())*/.build())
            .build()
            .create(ApiService::class.java)
    }

    single {
        PagingSourceNews(get(), get(), get())
    }

    single { AppDatabase.getInstance(androidContext()) }

    factory { get<AppDatabase>().newsDao }
    factory { NewsMapper() }
}
