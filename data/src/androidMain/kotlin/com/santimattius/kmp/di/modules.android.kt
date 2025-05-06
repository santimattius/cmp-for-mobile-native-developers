package com.santimattius.kmp.di

import app.cash.sqldelight.db.SqlDriver
import com.santimattius.kmp.data.db.DriverFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        /*factory<Context> {
            val context = applicationContext ?: run {
                throw IllegalArgumentException("Application context is null")
            }
            context
        }*/
        single<SqlDriver> { DriverFactory(context = androidContext()).createDriver() }
    }