package com.santimattius.kmp.skeleton.data

import cmp_for_mobile_native_developers.composeapp.generated.resources.Res
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
class JsonLoader {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    fun load(file: String): String = runBlocking {
        val jsonStr = Res.readBytes("files/${file}").decodeToString()
        jsonStr
    }

    internal inline fun <reified R : Any> load(file: String) =
        this.load(file).convertToDataClass<R>()

    internal inline fun <reified R : Any> String.convertToDataClass(): R {
        return json.decodeFromString<R>(this)
    }

}