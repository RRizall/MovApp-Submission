package com.rzl.movapp.core.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.rzl.movapp.core.data.AppRepository
import com.rzl.movapp.core.data.source.local.LocalDataSource
import com.rzl.movapp.core.data.source.local.room.PopularDatabase
import com.rzl.movapp.core.data.source.remote.RemoteDataSource
import com.rzl.movapp.core.data.source.remote.network.ApiService
import com.rzl.movapp.core.domain.repository.IPopularRepository
import com.rzl.movapp.core.utils.AppExecutors
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<PopularDatabase>().populerDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            PopularDatabase::class.java,"popular_db"
        ).fallbackToDestructiveMigration().build()
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single { AppExecutors() }
    single<IPopularRepository> {
        AppRepository(
            get(),
            get(),
            get()
        )
    }
}



val networkModule = module {
    single { AuthenticationInterceptor(apiKey = "56ea350c44428e5aa5246ad8a58067e5") }
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<AuthenticationInterceptor>())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

class AuthenticationInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        val urlWithApiKey = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()

        Log.d("API_LOG", "URL with API Key: ${urlWithApiKey.toString()}")

        val requestBuilder = originalRequest.newBuilder().url(urlWithApiKey)
        val request = requestBuilder.build()

        return chain.proceed(request)
    }
}



