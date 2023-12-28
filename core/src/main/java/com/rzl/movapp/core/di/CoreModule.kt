package com.rzl.movapp.core.di

import android.util.Log
import androidx.room.Room
import com.rzl.movapp.core.data.AppRepository
import com.rzl.movapp.core.data.source.local.LocalDataSource
import com.rzl.movapp.core.data.source.local.room.PopularDatabase
import com.rzl.movapp.core.data.source.remote.RemoteDataSource
import com.rzl.movapp.core.data.source.remote.network.ApiService
import com.rzl.movapp.core.domain.repository.IPopularRepository
import com.rzl.movapp.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
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
        val passphrase: ByteArray =  SQLiteDatabase.getBytes("rizal".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            PopularDatabase::class.java,"Popular.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
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
        val hostname = "api.themoviedb.org"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname,"sha256/5VLcahb6x4EvvFrCF2TePZulWqrLHS2jCg9Ywv6JHog=")
            .add(hostname,"sha256/vxRon/El5KuI4vx5ey1DgmsYmRY0nDd5Cg4GfJ8S+bg=")
            .add(hostname,"sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=")
            .add(hostname,"sha256/KwccWaCgrnaw6tsrrSO61FgLacNgG2MMLq8GE6+oP5I=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(get<AuthenticationInterceptor>())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
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

        Log.d("API_LOG", "URL with API Key: $urlWithApiKey")

        val requestBuilder = originalRequest.newBuilder().url(urlWithApiKey)
        val request = requestBuilder.build()

        return chain.proceed(request)
    }
}



