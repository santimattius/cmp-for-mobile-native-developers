package com.santimattius.kmp.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import com.santimattius.kmp.applicationContext
import com.santimattius.kmp.data.db.DriverFactory
import org.kodein.di.DI
import org.kodein.di.bindFactory
import org.kodein.di.bindSingleton
import org.kodein.di.instance

actual val platformModule: DI.Module
    get() = DI.Module("PlatformModule") {
        bindFactory<Unit, Context> {
            val context = applicationContext ?: run {
                throw IllegalArgumentException("Application context is null")
            }
            context
        }
        bindSingleton<SqlDriver> { DriverFactory(instance<Context>()).createDriver() }
    }