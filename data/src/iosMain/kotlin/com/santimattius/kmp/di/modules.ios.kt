package com.santimattius.kmp.di

import app.cash.sqldelight.db.SqlDriver
import com.santimattius.kmp.data.db.DriverFactory

actual val sqlDriver: SqlDriver
    get() = DriverFactory().createDriver()