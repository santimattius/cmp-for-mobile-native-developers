package com.santimattius.kmp.di

import app.cash.sqldelight.db.SqlDriver
import com.santimattius.kmp.applicationContext
import com.santimattius.kmp.data.db.DriverFactory

actual val sqlDriver: SqlDriver
    get() {
        val context = applicationContext ?: run {
            throw IllegalArgumentException("Application context is null")
        }
        return DriverFactory(context).createDriver()
    }