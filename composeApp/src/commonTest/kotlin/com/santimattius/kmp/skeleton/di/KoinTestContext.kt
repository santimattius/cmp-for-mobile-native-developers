package com.santimattius.kmp.skeleton.di

import org.koin.dsl.koinApplication

object KoinTestContext {

    private val koinApp = koinApplication {
        allowOverride(true)
        modules(applicationModules() + testingModule)
    }

    val koin = koinApp.koin
}