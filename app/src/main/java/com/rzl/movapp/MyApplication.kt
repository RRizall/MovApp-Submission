package com.rzl.movapp

import android.app.Application
import com.rzl.movapp.core.di.databaseModule
import com.rzl.movapp.core.di.networkModule
import com.rzl.movapp.core.di.repositoryModule
import com.rzl.movapp.di.useCaseModule
import com.rzl.movapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}