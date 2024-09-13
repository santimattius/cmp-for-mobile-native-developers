package com.santimattius.kmp.di

import app.cash.sqldelight.db.SqlDriver
import com.santimattius.kmp.data.db.DriverFactory
import org.kodein.di.DI
import org.kodein.di.bindSingleton

actual val platformModule: DI.Module
    get() = DI.Module("PlatformModule") {
        bindSingleton<SqlDriver> { DriverFactory().createDriver() }
    }