package com.stevecampos.composecleansample

import android.app.Application
import com.stevecampos.composecleansample.di.userListModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ComposeSampleApp: Application() {

    override fun onCreate() {
        super.onCreate()
        setupTimber()
        setupKoin()
    }

    private fun setupTimber() {

    }

    private fun setupKoin() {
        startKoin {
            androidLogger()
            androidContext(this@ComposeSampleApp)
            modules(userListModule)
        }
    }
}