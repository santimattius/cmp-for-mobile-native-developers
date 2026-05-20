package com.santimattius.kmp.skeleton.core.arch

import kotlinx.coroutines.flow.StateFlow

actual class StateFlowWrapper<T> actual constructor(private val flow: StateFlow<T>) {
    actual val value: T get() = flow.value
}
